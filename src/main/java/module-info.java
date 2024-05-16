module org.openjfx.cybooks {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.jfoenix;
    requires java.desktop;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;

    opens org.openjfx.cybooks to javafx.fxml;
    exports org.openjfx.cybooks;
}
