package de.mknjc.apps.hqpdown.ui.helper

import javafx.beans.property.ObjectProperty
import javafx.beans.property.StringProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StringPropertyDelegate(private val fxProperty : StringProperty) : ReadWriteProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return fxProperty.get()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        fxProperty.set(value)
    }

}
