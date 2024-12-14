package de.mknjc.apps.dnd

import de.mknjc.apps.hqpdown.ui.helper.IntegerPropertyDelegate
import de.mknjc.apps.hqpdown.ui.helper.StringPropertyDelegate
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

class Action {
    val sourceProperty = SimpleStringProperty(this, "source", "")
    fun sourceProperty() : StringProperty = sourceProperty
    var source by StringPropertyDelegate(sourceProperty)

    val targetProperty = SimpleStringProperty(this, "target", "")
    fun targetProperty() : StringProperty = targetProperty
    var target by StringPropertyDelegate(targetProperty)

    val damageProperty = SimpleIntegerProperty(this, "damage", 0)
    fun damageProperty() : IntegerProperty = damageProperty
    var damage by IntegerPropertyDelegate(damageProperty)

    val actionDescriptionProperty = SimpleStringProperty(this, "action", "")
    fun actionDescriptionProperty() : StringProperty = actionDescriptionProperty
    var actionDescription by StringPropertyDelegate(actionDescriptionProperty)


    override fun toString(): String {
        return "Action(source=$source, target=$target, damage=$damage, action=$actionDescription)"
    }
}
