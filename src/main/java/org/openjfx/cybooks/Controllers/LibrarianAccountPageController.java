package org.openjfx.cybooks.Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.CustomTextField;
import org.openjfx.cybooks.data.Librarian;
import org.openjfx.cybooks.database.DBHandler;

public class LibrarianAccountPageController{

    @FXML
    private Text firstname;

    @FXML
    private Text lastname;

    @FXML
    private Text login;

    @FXML
    private CustomTextField password;


    private Librarian librarian;



    public void setLibrarian(Librarian connectedLibrarian) {
        this.librarian = connectedLibrarian;
        firstname.setText(librarian.getFirstName());
        lastname.setText(librarian.getLastName());
        login.setText(DBHandler.getLibrarianLogin(librarian));
    }


}
