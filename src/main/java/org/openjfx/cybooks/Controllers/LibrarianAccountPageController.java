package org.openjfx.cybooks.Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.CustomTextField;
import org.openjfx.cybooks.data.Librarian;
import org.openjfx.cybooks.database.DBHandler;

/**
 * Controller class for the page that shows the information of the librarian (user)
 */
public class LibrarianAccountPageController{

    /**
     * Text used to show the firstname
     */
    @FXML
    private Text firstname;

    /**
     * Text used to show the lastname
     */
    @FXML
    private Text lastname;

    /**
     * Text used to show the login
     */
    @FXML
    private Text login;

    /**
     * The librarian to show
     */
    private Librarian librarian;


    /**
     * This method loads the information of the specified librarian on the page
     * @param connectedLibrarian
     */
    public void setLibrarian(Librarian connectedLibrarian) {
        this.librarian = connectedLibrarian;
        firstname.setText(librarian.getFirstName());
        lastname.setText(librarian.getLastName());
        login.setText(DBHandler.getLibrarianLogin(librarian));
    }


}
