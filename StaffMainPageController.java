package com.example.prototype;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StaffMainPageController implements Initializable {
    @FXML
    private Label UMmailOutputLabel;
    @FXML
    private Label staffIDOutputLabel;
    @FXML
    private Label nameOutputLabel;
    @FXML
    private Label genderOutputLabel;
    @FXML
    private Label nationalityOutputLabel;
    @FXML
    private Label ICNumberOutputLabel;
    @FXML
    private MenuBar StaffMainPageMenuBar;
    @FXML
    private final Stage mainLoginPageStage = new Stage();
    @FXML
    private final Stage staffSearchModulesPageStage = new Stage();
    @FXML
    private final Stage staffViewAllStudentsPageStage = new Stage();
    @FXML
    private final Stage staffModifyModulesPageStage = new Stage();
    @FXML
    private ImageView viewModulesiv;
    @FXML
    private ImageView viewAllStudentiv;
    @FXML
    private ImageView modifyModulesiv;
    @FXML
    public void modifyModules(ActionEvent event) throws Exception {
        System.out.println("Operation: modify modules");
        StaffModifyModulesPage staffModifyModulesPage = new StaffModifyModulesPage();
        staffModifyModulesPage.start(staffModifyModulesPageStage);
        Stage window = (Stage)StaffMainPageMenuBar.getScene().getWindow();
        window.close();
    }
    @FXML
    public void viewModules(ActionEvent event) throws Exception {
        System.out.println("Operation: view modules");
        StaffSearchModulesPage staffSearchModulesPage = new StaffSearchModulesPage();
        staffSearchModulesPage.start(staffSearchModulesPageStage);
        Stage window = (Stage)StaffMainPageMenuBar.getScene().getWindow();
        window.close();
    }
    @FXML
    public void viewAllStudent(ActionEvent event) throws Exception {
        System.out.println("Operation: view all registered students");
        StaffViewAllStudentPage staffViewAllStudentPage = new StaffViewAllStudentPage();
        staffViewAllStudentPage.start(staffViewAllStudentsPageStage);
        Stage window = (Stage)StaffMainPageMenuBar.getScene().getWindow();
        window.close();
    }
    @FXML
    public void logout(ActionEvent event) throws IOException {
        System.out.println("Operation: logout");
        MainLoginPage mainLoginPage = new MainLoginPage();
        mainLoginPage.start(mainLoginPageStage);
        Stage window = (Stage)StaffMainPageMenuBar.getScene().getWindow();
        window.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UMmailOutputLabel.setText(StaffClass.getUMmail().toUpperCase());
        staffIDOutputLabel.setText(StaffClass.getStaffID().toUpperCase());
        nameOutputLabel.setText(StaffClass.getName().toUpperCase());
        genderOutputLabel.setText(StaffClass.getGender().toUpperCase());
        nationalityOutputLabel.setText(StaffClass.getNationality().toUpperCase());
        ICNumberOutputLabel.setText(StaffClass.getICNumber().toUpperCase());
        //viewModulesiv
        Image searchIcon = new Image("C://Users//CHEN CHING YEN//Downloads//searchIcon.png");
        viewModulesiv.setImage(searchIcon);
        Image tableIcon = new Image("C://Users//CHEN CHING YEN//Downloads//tableIcon.png");
        viewAllStudentiv.setImage(tableIcon);
        //modifyModulesiv
        Image editIcon = new Image("C://Users//CHEN CHING YEN//Downloads//editIcon.png");
        modifyModulesiv.setImage(editIcon);
    }
}
