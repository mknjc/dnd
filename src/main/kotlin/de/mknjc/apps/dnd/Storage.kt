package de.mknjc.apps.dnd

import de.mknjc.apps.dnd.model.FighterType
import java.nio.file.Files
import java.nio.file.Path


class Storage {
    @kotlinx.serialization.Serializable
    data class Fighter(
        val type: FighterType = FighterType.ENEMY,
        val idx: Int = 0,
        val initiative: Int = 0,
        val name: String = "",
        val hp: Int = 0,
        val maxHp: Int = 0,
        val mods: String = "") {}

    fun loadFromFile(file: Path): List<Fighter> {
        return kotlinx.serialization.json.Json.decodeFromString(Files.readString(file))
    }
}
