module org.openjfx.cybooks {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires com.google.protobuf;
    requires java.desktop;
    requires java.sql;
    requires org.controlsfx.controls;
    requires de.jensd.fx.glyphs.fontawesome;
    requires spring.security.crypto;

    exports org.openjfx.cybooks;
    exports org.openjfx.cybooks.API;
    exports org.openjfx.cybooks.CommandLine;
    exports org.openjfx.cybooks.Controllers;
    exports org.openjfx.cybooks.data;
    exports org.openjfx.cybooks.database;
    exports org.openjfx.cybooks.UserInput;

    opens org.openjfx.cybooks to javafx.fxml;
    opens org.openjfx.cybooks.Controllers to javafx.fxml;
}
