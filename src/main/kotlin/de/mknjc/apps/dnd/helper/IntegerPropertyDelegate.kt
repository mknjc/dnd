package de.mknjc.apps.hqpdown.ui.helper

import javafx.beans.property.BooleanProperty
import javafx.beans.property.IntegerProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class IntegerPropertyDelegate(private val fxProperty : IntegerProperty) : ReadWriteProperty<Any?, Int> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fxProperty.get()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        fxProperty.set(value)
    }

}
