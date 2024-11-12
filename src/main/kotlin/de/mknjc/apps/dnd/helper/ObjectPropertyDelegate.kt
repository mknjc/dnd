package de.mknjc.apps.hqpdown.ui.helper

import javafx.beans.property.ObjectProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ObjectPropertyDelegate<T>(private val fxProperty : ObjectProperty<T>) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return fxProperty.get()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        fxProperty.set(value)
    }

}
