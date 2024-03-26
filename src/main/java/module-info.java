module com.example.soketprogrammeing {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.soketprogrammeing to javafx.fxml;
    exports com.example.soketprogrammeing;
}