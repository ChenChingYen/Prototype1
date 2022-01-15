package com.example.prototype;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentMainPageController implements Initializable {
    @FXML
    private final Stage mainLoginPageStage = new Stage();
    @FXML
    private final Stage studentSearchModulesPageStage = new Stage();
    @FXML
    private final Stage studentViewTimetablePageStage = new Stage();
    @FXML
    private final Stage studentRegisterModulesPageStage = new Stage();
    @FXML
    public Label siswamailOutputLabel;
    @FXML
    public Label matricNumberOutputLabel;
    @FXML
    public Label nameOutputLabel;
    @FXML
    public Label programmeOutputLabel;
    @FXML
    public Label genderOutputLabel;
    @FXML
    public Label nationalityOutputLabel;
    @FXML
    public Label ICNumberOutputLabel;
    @FXML
    public Label muetOutputLabel;
    @FXML
    public Label creditOutputLabel;
    @FXML
    public Label statusOutputLabel;
    @FXML
    private MenuBar StudentMainPageMenuBar;

    @FXML
    private ImageView timetableIconiv;
    @FXML
    private ImageView searchIconiv;
    @FXML
    private ImageView registerModulesiv;

    @FXML
    public void viewTimetable(ActionEvent event) throws Exception {
        if(StudentClass.getStatus()==0){
            System.out.println("Error: not yet registered to view timetable");
            Alert notRegisteredAlert = new Alert(Alert.AlertType.INFORMATION);
            notRegisteredAlert.setTitle("Maya 2.0");
            notRegisteredAlert.setHeaderText("Message:");
            notRegisteredAlert.setContentText("Student "+StudentClass.getMatricNumber()+" has not registered modules.\nYou can only view your timetable after modules registration");
            notRegisteredAlert.showAndWait();
        }else {
            System.out.println("Operation: view timetable");
            StudentViewTimetablePage studentViewTimetablePage = new StudentViewTimetablePage();
            studentViewTimetablePage.start(studentViewTimetablePageStage);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.close();
        }
    }
    @FXML
    public void searchModules(ActionEvent event) throws Exception { //
        System.out.println("Operation: search modules");
        StudentSearchModulesPage studentSearchModulesPage = new StudentSearchModulesPage();
        studentSearchModulesPage.start(studentSearchModulesPageStage);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
    @FXML
    public void registerModules(ActionEvent event) throws Exception {
        System.out.println("Operation: register modules");
        StudentRegisterModulesPage studentRegisterModulesPagePage = new StudentRegisterModulesPage();
        studentRegisterModulesPagePage.start(studentRegisterModulesPageStage);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
    @FXML
    public void logout(ActionEvent event) throws IOException {
        System.out.println("Operation: logout");
        MainLoginPage mainLoginPage = new MainLoginPage();
        mainLoginPage.start(mainLoginPageStage);
        Stage window = (Stage)StudentMainPageMenuBar.getScene().getWindow();
        window.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        siswamailOutputLabel.setText(StudentClass.getSiswamail().toUpperCase());
        matricNumberOutputLabel.setText(StudentClass.getMatricNumber().toUpperCase());
        nameOutputLabel.setText(StudentClass.getName().toUpperCase());
        programmeOutputLabel.setText(StudentClass.getProgramme().toUpperCase());
        genderOutputLabel.setText(StudentClass.getGender().toUpperCase());
        nationalityOutputLabel.setText(StudentClass.getNationality().toUpperCase());
        ICNumberOutputLabel.setText(StudentClass.getICNumber().toUpperCase());
        muetOutputLabel.setText(String.valueOf(StudentClass.getMuet()));
        creditOutputLabel.setText(String.valueOf(StudentClass.getCredit()));
        String status = "not enrolled";
        if(StudentClass.getStatus()==1){
            status = "enrolled";
        }
        statusOutputLabel.setText(status.toUpperCase());

        Image timetableIcon = new Image("C://Users//CHEN CHING YEN//Downloads//tableIcon.png");
        timetableIconiv.setImage(timetableIcon);
        Image searchIcon = new Image("C://Users//CHEN CHING YEN//Downloads//searchIcon.png");
        searchIconiv.setImage(searchIcon);
        //registerModulesiv
        Image registerIcon = new Image("C://Users//CHEN CHING YEN//Downloads//registerIcon.png");
        registerModulesiv.setImage(registerIcon);
    }
}
