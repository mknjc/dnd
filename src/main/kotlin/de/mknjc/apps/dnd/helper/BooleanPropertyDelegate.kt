package de.mknjc.apps.hqpdown.ui.helper

import javafx.beans.property.BooleanProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BooleanPropertyDelegate(private val fxProperty : BooleanProperty) : ReadWriteProperty<Any?, Boolean> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return fxProperty.get()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        fxProperty.set(value)
    }

}
