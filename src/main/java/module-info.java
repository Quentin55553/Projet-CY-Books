module org.openjfx.cybooks {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.openjfx.cybooks to javafx.fxml;
    exports org.openjfx.cybooks;
}