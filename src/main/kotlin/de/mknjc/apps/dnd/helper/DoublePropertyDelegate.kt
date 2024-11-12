package de.mknjc.apps.hqpdown.ui.helper

import javafx.beans.property.DoubleProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class DoublePropertyDelegate(private val fxProperty : DoubleProperty) : ReadWriteProperty<Any?, Double> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Double {
        return fxProperty.get()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Double) {
        fxProperty.set(value)
    }
}
