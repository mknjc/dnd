package de.mknjc.apps.dnd

import de.mknjc.apps.dnd.helper.editableTableCellForColumn
import de.mknjc.apps.hqpdown.ui.helper.BooleanPropertyDelegate
import de.mknjc.apps.hqpdown.ui.helper.ObjectPropertyDelegate
import javafx.beans.Observable
import javafx.beans.property.BooleanProperty
import javafx.beans.property.Property
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.TableColumn
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.input.MouseEvent
import javafx.util.Callback
import javafx.util.converter.IntegerStringConverter
import org.controlsfx.control.tableview2.TableColumn2
import org.controlsfx.control.tableview2.TableView2
import org.controlsfx.control.tableview2.cell.TextField2TableCell

class FighterTableView : TableView<Fighter>() {
    val activeFighters = FXCollections.observableArrayList<Fighter>()

    private val fightModeProperty = SimpleBooleanProperty(false)
    fun fightModeProperty() : BooleanProperty = fightModeProperty
    var fightMode by BooleanPropertyDelegate(fightModeProperty)


    private val buttonColumn = TableColumn<Fighter, Fighter>("Buttons").apply {
        cellFactory = fighterTableButtonCellForColumn(fightModeProperty.not())
        cellValueFactory = Callback { SimpleObjectProperty<Fighter>(it.value) }
        prefWidth = 70.0
    }
    private val initiativeColumn = TableColumn<Fighter, Int>("Initiative").apply {
        cellFactory = TextFieldTableCell.forTableColumn<Fighter, Int>(IntegerStringConverter())
        cellValueFactory = PropertyValueFactory("initiative")
        prefWidth = 70.0
    }
    val nameColumn = TableColumn<Fighter, String>("Name").apply {
        cellFactory = TextFieldTableCell.forTableColumn<Fighter>()
        cellValueFactory = PropertyValueFactory("name")
        isEditable = true
        prefWidth = 400.0
    }
    private val hpColumn = TableColumn<Fighter, Int>("HP").apply {
        cellFactory = TextFieldTableCell.forTableColumn<Fighter, Int>(IntegerStringConverter())
        cellValueFactory = PropertyValueFactory("hp")
        prefWidth = 70.0
    }
    private val maxHpColumn = TableColumn<Fighter, Int>("MaxHP").apply {
        cellFactory = TextFieldTableCell.forTableColumn<Fighter, Int>(IntegerStringConverter())
        cellValueFactory = PropertyValueFactory("maxHp")
        prefWidth = 70.0
    }
    private val modsColumn = TableColumn<Fighter, String>("Mods").apply {
        cellFactory = TextFieldTableCell.forTableColumn<Fighter>()
        cellValueFactory = PropertyValueFactory("mods")
        prefWidth = 500.0
    }

    init {
        isEditable = true

        this.rowFactory = Callback { rowFactory(it) }

        columns.addAll(buttonColumn, initiativeColumn, nameColumn, hpColumn, maxHpColumn, modsColumn)

        fightModeProperty.addListener { _ : Observable ->
            this.refresh()
        }
    }

    private fun rowFactory(table: TableView<Fighter>) : TableRow<Fighter> {
        val row = object : TableRow<Fighter>() {
            init {
                activeFighters.addListener { obs : Observable ->
                    updateItem(item, isEmpty)
                }
            }
            override fun updateItem(item: Fighter?, empty: Boolean) {
                super.updateItem(item, empty)

                if (activeFighters.contains(item)) {
                    style = "-fx-background-color: lightgreen;"
                } else {
                    style = ""
                }
            }
        }
        return row
    }
}
