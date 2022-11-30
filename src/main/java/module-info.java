module tarik.inc.teachersapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens tarik.inc.teachersapp to javafx.fxml;
    exports tarik.inc.teachersapp;
}