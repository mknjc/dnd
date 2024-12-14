package de.mknjc.apps.dnd

import javafx.application.Platform.runLater
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.stage.Modality
import javafx.util.converter.IntegerStringConverter
import org.controlsfx.control.CheckComboBox


class AddActionDialog(
    parentScene : Scene,
    fighters : List<String>,
    private val selectFirst : Boolean
    ) : Dialog<List<Action>>() {
    @FXML private lateinit var sourceSelector : CheckComboBox<String>
    @FXML private lateinit var targetSelector : CheckComboBox<String>
    @FXML private lateinit var damageField : TextField
    @FXML private lateinit var actionDescriptionField : TextField

    init {
        initOwner(parentScene.window)

        val loader = FXMLLoader()
        loader.location = javaClass.getResource("add_action_dialog.fxml")
        loader.setController(this)
        val dialogPane = loader.load<DialogPane>()
        setDialogPane(dialogPane)

        isResizable = true
        title = "Add Action"

        sourceSelector.items.addAll(fighters)
        targetSelector.items.addAll(fighters)

        if (selectFirst) {
            sourceSelector.checkModel.check(0)
            runLater { targetSelector.requestFocus() }
        } else {
            runLater { sourceSelector.requestFocus() }
        }

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
                action.actionDescription = actionDescriptionField.text

                ret.add(action)
            }
        }
        return ret
    }
}
