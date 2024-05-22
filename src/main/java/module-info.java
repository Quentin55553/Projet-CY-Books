module org.openjfx.cybooks {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.jfoenix;
    requires java.desktop;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    requires spring.security.crypto;

    opens org.openjfx.cybooks to javafx.fxml;
    exports org.openjfx.cybooks;
    exports org.openjfx.cybooks.Controllers;
    opens org.openjfx.cybooks.Controllers to javafx.fxml;
}
