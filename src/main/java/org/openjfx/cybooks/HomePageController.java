package org.openjfx.cybooks;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HomePageController implements Initializable {
    @FXML
    private Label IDLabel;
    @FXML
    private JFXButton AddCustomerTopButton;
    @FXML
    private JFXButton AddCustomerLeftButton;
    @FXML
    private JFXButton AddLoanLeftButton;
    @FXML
    private JFXButton AddLoanTopButton;
    @FXML
    private JFXButton SearchUserLeftButton;
    @FXML
    private JFXButton SearchUserTopButton;
    @FXML
    private JFXButton SearchBookLeftButton;
    @FXML
    private JFXButton SearchBookTopButton;
    @FXML
    private JFXButton AllUsersButton;
    @FXML
    private JFXButton AllLoansButton;
    @FXML
    private JFXButton LoansIssuesButton;

    @FXML
    public AnchorPane Center;
    @FXML
    private JFXHamburger MenuHamb;
    @FXML
    private AnchorPane Menuside;
    private Main main;


    // Default constructor
    public HomePageController() {
    }


    public void setMain(Main main) {
        this.main = main;
    }


    public void setPrimaryStage(Stage primaryStage) {
    }


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
        AddCustomerTopButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/addCustomer-page.fxml"));
        AddCustomerLeftButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/addCustomer-page.fxml"));
        AddLoanTopButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/addLoan-page.fxml"));
        AddLoanLeftButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/addLoan-page.fxml"));
        SearchUserLeftButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/SearchUser-page.fxml"));
        SearchUserTopButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/SearchUser-page.fxml"));
        SearchBookLeftButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/SearchBook-page.fxml"));
        SearchBookTopButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/SearchBook-page.fxml"));
        AllUsersButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/AllUsers-page.fxml"));
        AllLoansButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/AllLoans-page.fxml"));
        LoansIssuesButton.setOnAction(actionEvent -> handleChangeCenter("/org/openjfx/cybooks/LoansIssues-page.fxml"));
    }


    public void setLibrarianID(String id) {
        IDLabel.setText(id);
    }

    public void logoutClicked() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Vous êtes sur le point de vous déconnecter");
        alert.setContentText("Voulez-vous vraiment vous déconnecter ?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            main.showLogInScene();
        }
    }


    public void handleChangeCenter(String fxmlFile) {
        try {
            // Call the changeCenter method with the specified path
            changeCenter(fxmlFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
