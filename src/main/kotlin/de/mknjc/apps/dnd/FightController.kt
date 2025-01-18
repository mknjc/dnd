package de.mknjc.apps.dnd

import de.mknjc.apps.dnd.model.FighterType
import javafx.beans.binding.When
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.Callback
import javafx.util.converter.IntegerStringConverter
import java.net.URL
import java.util.*
import java.util.Comparator.comparing
import java.util.Comparator.comparingInt

class FightController : Initializable {

    @FXML
    private lateinit var roundLabel: Label

    @FXML
    private lateinit var addFighterButton: Button

    @FXML
    private lateinit var nextRoundButton: Button

    @FXML
    private lateinit var startFightButton: Button

    @FXML
    private lateinit var tablePlane: SplitPane

    private val fightersTable = FighterTableView()

    @FXML
    private lateinit var actionsTable: TableView<Action>

    @FXML
    private lateinit var actionsSourceColumn: TableColumn<Action, String>

    @FXML
    private lateinit var actionsTargetColumn: TableColumn<Action, String>

    @FXML
    private lateinit var actionsDamageColumn: TableColumn<Action, Int>

    @FXML
    private lateinit var actionsActionColumn: TableColumn<Action, String>

    private val appliedActions = mutableListOf<Action>()

    private var currentIni = 0

    private val fightMode = SimpleBooleanProperty(false)


    fun addFighter(fighter: Fighter) {
        fightersTable.items.add(fighter)
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        tablePlane.items.addFirst(fightersTable)

        //fightersTable.fightModeProperty().bind(fightMode)
        startFightButton.textProperty().bind(When(fightMode).then("End Fight").otherwise("Start Fight"))
        nextRoundButton.disableProperty().bind(fightMode.not())
        //addFighterButton.disableProperty().bind(fightMode)


//        fightersTable.onMouseClicked = EventHandler<MouseEvent> { event ->
//            val pick = event.pickResult
//            var source = pick.intersectedNode
//
//            while (source != null && source !is TableRow<*>) {
//                source = source.parent
//            }
//
//            if (source is TableRow<*>) {
//                val row = source as TableRow<*>
//                if (row.isEmpty) {
//                    addFighter()
//                }
//            }
//        }

        actionsSourceColumn.cellFactory = TextFieldTableCell.forTableColumn()
        actionsTargetColumn.cellFactory = TextFieldTableCell.forTableColumn()
        actionsDamageColumn.cellFactory = TextFieldTableCell.forTableColumn(IntegerStringConverter())
        actionsActionColumn.cellFactory = TextFieldTableCell.forTableColumn()
//
//        actionsTable.onMouseClicked = EventHandler<MouseEvent> { event ->
//            val pick = event.pickResult
//            var source = pick.intersectedNode
//
//            while (source != null && source !is TableRow<*>) {
//                source = source.parent
//            }
//
//            if (source is TableRow<*>) {
//                val row = source as TableRow<*>
//                if (row.isEmpty) {
//                    addAction()
//                }
//            } else {
//                addAction()
//            }
//        }

        actionsTable.rowFactory = Callback { actionTableRowFactory() }
    }

    private fun actionTableRowFactory(): TableRow<Action> {
        val row = object : TableRow<Action>() {
            override fun updateItem(item: Action?, empty: Boolean) {
                super.updateItem(item, empty)

                if (appliedActions.contains(item)) {
                    style = "-fx-background-color: lightblue;"
                } else {
                    style = ""
                }
            }
        }
        return row
    }

    @FXML
    fun addFighterClicked(event: ActionEvent) {
        addFighter()
    }

    @FXML
    fun startFightPressed(event: ActionEvent) {
        fightMode.set(!fightMode.get())

        if (fightMode.get()) {
            fightersTable.items.forEachIndexed { idx, fighter ->
                fighter.idx = idx
            }
            nextRoundPressed(event)
        } else {
            fightersTable.items.sortBy { fighter -> fighter.idx }
            fightersTable.activeFighters.clear()
            applyActions()

            currentIni = 0

            actionsTable.items.clear()

            fightersTable.items.removeAll { fighter -> fighter.name.isBlank() || fighter.type == FighterType.ENEMY }

            fightersTable.items.forEach { fighter -> fighter.initiative = 0 }
        }
    }

    @FXML
    fun nextRoundPressed(event: ActionEvent) {
        fightersTable.items.removeAll { fighter -> fighter.name.isBlank() }

        applyActions()
        fightersTable.items.sortByDescending { fighter -> fighter.initiative }

        val nextFighters = mutableListOf<Fighter>()
        var nextIni = 0

        println("previous initiative: $currentIni")

        fightersTable.items
            .filter { fighter -> fighter.isParticipatingInFight() }
            .filter { fighter -> fighter.initiative < currentIni }
            .forEach { fighter ->
                val ini = fighter.initiative
                if (ini > nextIni) {
                    nextFighters.clear()
                    nextIni = ini
                }
                if (ini == nextIni) {
                    nextFighters.add(fighter)
                }
            }

        if (nextFighters.isNotEmpty()) {
            println("Next fighters in this round: $nextFighters")
            println("Initiative: $nextIni")

        }

        if (nextFighters.isEmpty()) {
            nextIni = fightersTable.items
                .filter { fighter -> fighter.isParticipatingInFight() }
                .maxOfOrNull { fighter -> fighter.initiative } ?: 0
            fightersTable.items
                .filter { fighter -> fighter.isParticipatingInFight() }
                .filter { fighter -> fighter.initiative == nextIni }
                .forEach { fighter -> nextFighters.add(fighter) }

            println("New round with initiative: $nextIni")
            println("Next fighters in this round: $nextFighters")
        }

        currentIni = nextIni

        fightersTable.activeFighters.clear()
        fightersTable.activeFighters.addAll(nextFighters)

    }

    private fun applyActions() {
        actionsTable.items.forEach { action ->
            if (!appliedActions.contains(action) && action.source.isNotBlank() && action.target.isNotBlank()) {

                appliedActions.add(action)

                println("Action Log Action: $action")

                val targetFighter = fightersTable.items.find { fighter -> fighter.name == action.target }
                if (targetFighter != null) {
                    println("Action Log Apply ${action.damage} damage from ${action.source} damage to $targetFighter")
                    val prevHp = targetFighter.hp
                    targetFighter.damage(action.damage)
                    println("Action Log HP: $prevHp -> ${targetFighter.hp}")
                } else {
                    println("Action Log Target ${action.target} not found")
                }
            }
        }
        actionsTable.refresh()
    }

    @FXML
    fun addActionPressed(event: ActionEvent) {
        val isSelectedComparator = comparingInt<Fighter> { Math.abs(it.initiative - currentIni) }

        val fightersForSelection = fightersTable.items.sorted(isSelectedComparator.then(comparing(Fighter::name))).map(Fighter::name)
        val isOnlyOneFighterSelected = fightersTable.activeFighters.size == 1

        AddActionDialog(actionsTable.scene, fightersForSelection,isOnlyOneFighterSelected).showAndWait().ifPresent { actions ->
            actionsTable.items.addAll(actions)
        }
    }

    private fun addFighter() {
        fightersTable.items.add(Fighter(FighterType.PARTY, 0, 0, "", 0, 0, ""))
        fightersTable.focusModel.focus(fightersTable.items.size - 1, fightersTable.nameColumn)
        fightersTable.requestFocus()
        fightersTable.edit(fightersTable.items.size - 1, fightersTable.nameColumn)
    }
}
