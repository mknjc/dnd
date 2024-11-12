package de.mknjc.apps.dnd

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import java.nio.file.Path

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("fight.fxml"))
        val scene = Scene(fxmlLoader.load(), 1280.0, 720.0)

        stage.title = "Hello!"
        stage.scene = scene
        stage.show()

        val controller = fxmlLoader.getController<FightController>()

        try {
            Storage().loadFromFile(Path.of("fighters.json")).forEach {
                val fighter = Fighter(it.type, it.idx, it.initiative, it.name, it.hp, it.maxHp, it.mods)
                controller.addFighter(fighter)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}
