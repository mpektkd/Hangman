module com.example.hangman {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires org.json;
    requires java.logging;

    opens com.example.hangman to javafx.fxml;
    exports com.example.hangman;
}