package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.openjfx.cybooks.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the home page of the app
 */
public class HomePageController implements Initializable {
    /**
     * Label used to show the user's ID
     */
    @FXML
    private Label IDLabel;
    /**
     * Button used to travel to the AddCustomer page
     */
    @FXML
    private JFXButton AddCustomerTopButton;
    /**
     * Button used to travel to the AddCustomer page
     */
    @FXML
    private JFXButton AddCustomerLeftButton;
    /**
     * Button used to travel to the AddLoan page
     */
    @FXML
    private JFXButton AddLoanLeftButton;
    /**
     * Button used to travel to the AddLoan page
     */
    @FXML
    private JFXButton AddLoanTopButton;
    /**
     * Button used to travel to the SearchUser page
     */
    @FXML
    private JFXButton SearchUserLeftButton;
    /**
     * Button used to travel to the SearchUser page
     */
    @FXML
    private JFXButton SearchUserTopButton;
    /**
     * Button used to travel to the SearchBook page
     */
    @FXML
    private JFXButton SearchBookLeftButton;
    /**
     * Button used to travel to the SearchBook page
     */
    @FXML
    private JFXButton SearchBookTopButton;
    /**
     * Button used to travel to the AllUsers page
     */
    @FXML
    private JFXButton AllUsersButton;
    /**
     * Button used to travel to the AllLoans page
     */
    @FXML
    private JFXButton AllLoansButton;
    /**
     * Button used to travel to the LoanIssues page
     */
    @FXML
    private JFXButton LoansIssuesButton;

    /**
     * The center of the page
     */
    @FXML
    public AnchorPane Center;
    /**
     * The logo
     */
    @FXML
    private JFXHamburger MenuHamb;
    /**
     * The menu sidebar
     */
    @FXML
    private AnchorPane Menuside;
    /**
     * Reference to the main application
     */
    private Main main;


    /**
     * Default constructor of the HomePageController class
     */
    public HomePageController() {
    }


    /**
     * Setter for the main attribute
     * @param main Reference to the main application
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * Setter for the primary stage attribute
     * @param primaryStage The main container
     */
    public void setPrimaryStage(Stage primaryStage) {
    }

    /**
     * This method handles the sliding of the menu when the logo is clicked and sets up each button's action
     * @param url Not used
     * @param resourceBundle Not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Hide menu
        Menuside.setTranslateX(-208);
        
        MenuHamb.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.3));
            slide.setNode(Menuside);

            // Check if the menu is currently hidden
            if (Menuside.getTranslateX() != 0) {
                // Slide the menu in
                slide.setToX(0);
                slide.play();

            } else {
                // Slide the menu out
                slide.setToX(-208);
                slide.play();
            }

            // Toggle the hamburger icon animation
            HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(MenuHamb);
            transition.setRate(-1);
            transition.play();
        });

        // top and left menu buttons
        AddCustomerTopButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/fxmlFiles/addCustomer-page.fxml"));
        AddCustomerLeftButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/fxmlFiles/addCustomer-page.fxml"));
        AddLoanTopButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/fxmlFiles/addLoan-page.fxml"));
        AddLoanLeftButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/fxmlFiles/addLoan-page.fxml"));
        SearchUserLeftButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/fxmlFiles/SearchUser-page.fxml"));
        SearchUserTopButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/fxmlFiles/SearchUser-page.fxml"));
        SearchBookLeftButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/fxmlFiles/SearchBook-page.fxml"));
        SearchBookTopButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/fxmlFiles/SearchBook-page.fxml"));
        AllUsersButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/fxmlFiles/AllUsers-page.fxml"));
        AllLoansButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/fxmlFiles/AllLoans-page.fxml"));
        LoansIssuesButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/fxmlFiles/LoansIssues-page.fxml"));
    }


    /**
     * This method is used to set the text of the ID label on the view
     * @param id The ID to show (String)
     */
    public void setLibrarianID(String id) {
        IDLabel.setText(id);
    }

    /**
     * This method is used when the user clicks on the 'disconnect' button
     * @throws IOException If an error occurs when showing the login page again
     */
    public void logoutClicked() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Vous êtes sur le point de vous déconnecter");
        alert.setContentText("Voulez-vous vraiment vous déconnecter ?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            main.showLogInScene();
        }
    }


    /**
     * This method is called to change the view of the page.
     * It uses changeCenter() for that
     * @param fxmlFile The new fxml file to use
     */
    public void handleChangeCenter(String fxmlFile) {
        try {
            // Call the changeCenter method with the specified path
            changeCenter(fxmlFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used to change the view of the page
     * @param fxmlFile The new fxml file to use
     * @throws IOException If loading of the file fails
     */
    public void changeCenter(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        AnchorPane newCenter = loader.load();
        // Set the size constraints of the new AnchorPane
        newCenter.setPrefSize(Center.getPrefWidth(), Center.getPrefHeight());

        AnchorPane.setTopAnchor(newCenter, 0.0);
        AnchorPane.setLeftAnchor(newCenter, 210.0);
        AnchorPane.setBottomAnchor(newCenter, 0.0);
        AnchorPane.setRightAnchor(newCenter, 210.0);

        Center.getChildren().setAll(newCenter);
        /*
        // Transfer children from newCenter to Center
        Center.getChildren().setAll(newCenter.getChildren());
        */
    }


}
