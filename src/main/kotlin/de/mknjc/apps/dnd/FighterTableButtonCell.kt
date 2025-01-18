package de.mknjc.apps.dnd

import de.mknjc.apps.dnd.model.FighterType
import javafx.beans.value.ObservableBooleanValue
import javafx.scene.control.Button
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.Tooltip
import javafx.scene.text.Text
import javafx.util.Callback
import org.kordamp.ikonli.javafx.FontIcon

fun <S> fighterTableButtonCellForColumn(editable: ObservableBooleanValue): Callback<TableColumn<S, Fighter>, TableCell<S, Fighter>> {
    return Callback { FighterTableButtonCell<S>(editable) }
}
class FighterTableButtonCell<S>(private val editable: ObservableBooleanValue) : TableCell<S, Fighter>() {
    private val sword = FontIcon("mdi2s-sword:28")
    private val group = FontIcon("mdi2a-account-group:28")
    private val groupDisabled = FontIcon("mdi2a-account-group-outline:28")
    private val shield = FontIcon("mdi2s-shield-half-full:28")

    private val tt = Tooltip("Change type")
    private val button = Button("", shield).apply {
        tooltip = tt
        setOnAction {
            item?.type = when (item?.type) {
                FighterType.PARTY -> FighterType.DISABLED_PARTY
                FighterType.DISABLED_PARTY -> FighterType.FRIEND
                FighterType.FRIEND -> FighterType.ENEMY
                FighterType.ENEMY -> FighterType.PARTY
                null -> TODO()
            }
            updateItem(item, isEmpty)
        }
    }

    private fun iconFromType(figtherType: FighterType): Text {
        return when (figtherType) {
            FighterType.PARTY -> group
            FighterType.DISABLED_PARTY -> groupDisabled
            FighterType.FRIEND -> shield
            FighterType.ENEMY -> sword
        }
    }

    override fun updateItem(item: Fighter?, empty: Boolean) {
        super.updateItem(item, empty)

        if (item == null || empty) {
            text = null
            graphic = null
        } else {
            val icon = iconFromType(item.type)

            if (editable.get()) {
                text = null
                button.graphic = icon
                graphic = button
            } else {
                text = null
                graphic = icon
            }
        }
    }
}
