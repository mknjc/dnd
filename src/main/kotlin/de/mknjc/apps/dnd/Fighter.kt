package de.mknjc.apps.dnd

import de.mknjc.apps.hqpdown.ui.helper.IntegerPropertyDelegate
import de.mknjc.apps.hqpdown.ui.helper.ObjectPropertyDelegate
import de.mknjc.apps.hqpdown.ui.helper.StringPropertyDelegate
import javafx.beans.property.IntegerProperty
import javafx.beans.property.Property
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

enum class FighterType {
    PARTY,
    DISABLED_PARTY,
    FRIEND,
    ENEMY
}

class Fighter() {
    val typeProperty = SimpleObjectProperty(this, "type", FighterType.ENEMY)
    fun typeProperty() : Property<FighterType> = typeProperty
    var type by ObjectPropertyDelegate(typeProperty)

    val idxProperty = SimpleIntegerProperty(this, "idx", 0)
    fun idxProperty() : IntegerProperty = idxProperty
    var idx by IntegerPropertyDelegate(idxProperty)

    val initiativeProperty = SimpleIntegerProperty(this, "initiative", 0)
    fun initiativeProperty() : IntegerProperty = initiativeProperty
    var initiative by IntegerPropertyDelegate(initiativeProperty)

    val nameProperty = SimpleStringProperty(this, "name", "")
    fun nameProperty() : StringProperty = nameProperty
    var name by StringPropertyDelegate(nameProperty)

    val hpProperty = SimpleIntegerProperty(this, "hp", 0)
    fun hpProperty() : IntegerProperty = hpProperty
    var hp by IntegerPropertyDelegate(hpProperty)

    val maxHpProperty = SimpleIntegerProperty(this, "maxHp", 0)
    fun maxHpProperty() : IntegerProperty = maxHpProperty
    var maxHp by IntegerPropertyDelegate(maxHpProperty)

    val modsProperty = SimpleStringProperty(this, "mods", "")
    fun modsProperty() : StringProperty = modsProperty
    var mods by StringPropertyDelegate(modsProperty)


    constructor(type: FighterType, idx: Int, initiative: Int, name: String, hp: Int, maxHp: Int, mods: String) : this() {
        this.type = type
        this.idx = idx
        this.initiative = initiative
        this.name = name
        this.hp = hp
        this.maxHp = maxHp
        this.mods = mods
    }

    fun isAlive() : Boolean = if (maxHp >= 0) hp > 0 else true

    fun damage(damage: Int) {
        if (maxHp >= 0)
            hp = 0.coerceAtLeast(hp - damage)
        else
            hp += damage
    }

    fun heal(heal: Int) {
        if (maxHp >= 0)
            hp = maxHp.coerceAtMost(hp + heal)
        else
            hp -= heal
    }

    fun isParticipatingInFight() : Boolean {
        if (initiative == 0) return false
        when (type) {
            FighterType.PARTY -> return true
            FighterType.DISABLED_PARTY -> return false
            FighterType.FRIEND -> return isAlive()
            FighterType.ENEMY -> return isAlive()
        }
    }

    override fun toString(): String {
        return "Fighter(name=$name, type=$type, idx=$idx, initiative=$initiative, hp=$hp, maxHp=$maxHp, mods=$mods)"
    }
}
