module org.openjfx.cybooks {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.java;

    opens org.openjfx.cybooks to javafx.fxml;
    exports org.openjfx.cybooks;
}