package de.mknjc.apps.dnd

import javafx.event.Event
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.*
import javafx.stage.Modality
import javafx.util.converter.IntegerStringConverter
import org.controlsfx.control.CheckComboBox


class AddActionDialog(private val fighters : List<String>) : Dialog<List<Action>>() {
    @FXML private lateinit var sourceSelector : CheckComboBox<String>
    @FXML private lateinit var targetSelector : CheckComboBox<String>
    @FXML private lateinit var damageField : TextField



    init {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("add_action_dialog.fxml")
        loader.setController(this)
        val dialogPane = loader.load<DialogPane>()
        setDialogPane(dialogPane)

        isResizable = true
        title = "Add Action"

        sourceSelector.items.addAll(fighters)
        targetSelector.items.addAll(fighters)

        setResultConverter(this::resultConverter)

        damageField.textFormatter = TextFormatter(IntegerStringConverter())

    }

    private fun resultConverter(buttonType: ButtonType): List<Action>? {
        if (ButtonBar.ButtonData.OK_DONE != buttonType.buttonData) {
            return null
        }
        val ret = arrayListOf<Action>()

        sourceSelector.checkModel.checkedItems.forEach { source ->
            targetSelector.checkModel.checkedItems.forEach { target ->
                val action = Action()
                action.source = source
                action.target = target
                action.damage = damageField.text.toIntOrNull() ?: 0

                ret.add(action)
            }
        }
        return ret
    }
}
