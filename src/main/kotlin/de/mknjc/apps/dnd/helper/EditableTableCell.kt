package de.mknjc.apps.dnd.helper

import javafx.beans.value.ObservableBooleanValue
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TextField
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.Callback

fun <S> editableIntTableCellForColumn(editable: ObservableBooleanValue) : Callback<TableColumn<S, Int>, TableCell<S, Int>> {
    return object : Callback<TableColumn<S, Int>, TableCell<S, Int>> {

        override fun call(param: TableColumn<S, Int>?): TableCell<S, Int> {
            return EditableTableCell<S, Int>(editable)
        }
    }
}

fun <S, T> editableTableCellForColumn(editable: ObservableBooleanValue) : Callback<TableColumn<S, T>, TableCell<S, T>> {
    return object : Callback<TableColumn<S, T>, TableCell<S, T>> {
        override fun call(param: TableColumn<S, T>?): TableCell<S, T> {
            return EditableTableCell<S, T>(editable)
        }
    }
}

class EditableTableCell<S, T>(val editable: ObservableBooleanValue) : TableCell<S, T>() {
    private val textField = TextField()

    init {
        editable.addListener { obs ->
            updateItem(item, isEmpty)
        }
    }

    override fun updateItem(item: T?, empty: Boolean) {
        super.updateItem(item, empty)

        if (item == null || empty) {
            text = null
            graphic = null
        } else {
            if (editable.get()) {
                text = null
                textField.text = item.toString()
                graphic = textField
            } else {
                graphic = null
                text = item.toString()
            }
        }
    }
}
