module de.mknjc.apps.dnd {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires kotlinx.serialization.core;
    requires kotlinx.serialization.json;

    requires org.controlsfx.controls;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.material2;
    requires org.kordamp.ikonli.materialdesign2;

    opens de.mknjc.apps.dnd to javafx.fxml;
    exports de.mknjc.apps.dnd;
}
