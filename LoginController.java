package com.example.prototype;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private ImageView mayaLogoiv;
    @FXML
    private TextField usernameTF;
    @FXML
    private TextField passwordTF;
    @FXML
    private ChoiceBox<String> statusCB;

    String usernameInput;
    String passwordInput;
    String ePassword;
    String status;
    String[] statusList = {"Student", "Staff"};

    PasswordEncryption encryption = new PasswordEncryption();

    @FXML
    private final Stage studentRegisterStage = new Stage();
    @FXML
    private final Stage staffRegisterStage = new Stage();
    @FXML
    private final Stage studentMainStage = new Stage();
    @FXML
    private final Stage staffMainStage = new Stage();

    String siswamail;
    String matricNumber;
    String name;
    String programme;
    String gender;
    String nationality;
    String ICNumber;
    int muet;
    int credit;
    int enrollment;
    String loginPassword;

    String UMmail;
    String staffID;

    @FXML
    public void login(ActionEvent event) throws IOException {

        //login logic
        if(usernameTF.getText().equals("")||passwordTF.getText().equals("")){
            System.out.println("Error: incomplete input");
            Alert emptyInputAlert = new Alert(Alert.AlertType.ERROR);
            emptyInputAlert.setTitle("Invalid Input");
            emptyInputAlert.setHeaderText("Please fill in information required");
            emptyInputAlert.setContentText("Please try again");
            emptyInputAlert.showAndWait();
        }else {
            System.out.println("Operation: login");
            usernameInput = usernameTF.getText();
            System.out.println("username: "+usernameInput);
            passwordInput = passwordTF.getText();
            System.out.println("password: "+passwordInput);
            ePassword = encryption.encryption(passwordInput);
            System.out.println("encrypted password: "+ePassword);
            status = statusCB.getValue();
            System.out.println("status: "+status);

            if (status.equalsIgnoreCase("student")) {

                // jdbc
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
                    java.sql.Statement sqlStatement = conn.createStatement();
                    String sql = "";
                    sql = "SELECT * FROM student_info WHERE matric_number = \'" + usernameInput.toLowerCase() + "\'";
                    ResultSet results = sqlStatement.executeQuery(sql);
                    System.out.println("Student information:");
                    while (results.next()) {
                        siswamail = (String) results.getObject(1);
                        System.out.println("siswwamail: "+siswamail);
                        matricNumber = (String) results.getObject(2);
                        System.out.println("matric number: "+matricNumber);
                        name = (String) results.getObject(3);
                        System.out.println("name: "+name);
                        programme = (String) results.getObject(4);
                        System.out.println("programme: "+programme);
                        gender = (String) results.getObject(5);
                        System.out.println("gender: "+gender);
                        nationality = (String) results.getObject(6);
                        System.out.println("nationality: "+nationality);
                        ICNumber = (String) results.getObject(7);
                        System.out.println("ic number: "+ICNumber);
                        muet = (int) results.getObject(8);
                        System.out.println("muet: "+muet);
                        credit = (int) results.getObject(9);
                        System.out.println("credit: "+credit);
                        loginPassword = (String) results.getObject(10);
                        enrollment = (int) results.getObject(11);
                        System.out.println("status: "+enrollment);
                    }
                    StudentClass studentObj = new StudentClass(siswamail, matricNumber, name, programme, gender, nationality, ICNumber, muet, credit, enrollment);
                    conn.close();
                } catch (Exception connectionError) {
                    System.out.println("Error: class build failed");
                    System.out.println(connectionError);
                }
                // end jdbc

//            System.out.println(ePassword);
//            System.out.println(loginPassword);
                if (ePassword.equals(loginPassword)) {
                    System.out.println("Operation: login for student");
                    StudentMainPage studentMainPage = new StudentMainPage();
                    studentMainPage.start(studentMainStage);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.close();
                } else {
                    Alert wrongPasswordAlert = new Alert(Alert.AlertType.ERROR);
                    wrongPasswordAlert.setTitle("Wrong Password");
                    wrongPasswordAlert.setHeaderText("Incompatible password for matric number: " + usernameInput);
                    wrongPasswordAlert.setContentText("Please try again");
                    wrongPasswordAlert.showAndWait();
                    System.out.println("Error: wrong password");
                    passwordTF.setText("");
                }
            } else if (status.equalsIgnoreCase("staff")) { // STAFF
                System.out.println("Operation: login for staff");
                // staff logic

                // jdbc
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
                    java.sql.Statement sqlStatement = conn.createStatement();
                    String sql = "";
                    sql = "SELECT * FROM staff_info WHERE staff_id = \'" + usernameInput.toLowerCase() + "\'";
                    ResultSet results = sqlStatement.executeQuery(sql);
                    System.out.println("Staff information:");
                    while (results.next()) {
                        UMmail = (String) results.getObject(1);
                        System.out.println("UM mail: "+UMmail);
                        staffID = (String) results.getObject(2);
                        System.out.println("staff id: "+staffID);
                        name = (String) results.getObject(3);
                        System.out.println("name: "+name);
                        gender = (String) results.getObject(4);
                        System.out.println("gender: "+gender);
                        nationality = (String) results.getObject(5);
                        System.out.println("nationality: "+nationality);
                        ICNumber = (String) results.getObject(6);
                        System.out.println("ic number: "+ICNumber);
                        loginPassword = (String) results.getObject(7);
                    }
                    StaffClass staffObj = new StaffClass(UMmail, staffID, name, gender, nationality, ICNumber);
                    conn.close();
                } catch (Exception connectionError) {
                    System.out.println("Error: class build failed");
                    System.out.println(connectionError);
                }
                // end jdbc

//            System.out.println(ePassword);
//            System.out.println(loginPassword);
                if (ePassword.equals(loginPassword)) {
                    StaffMainPage staffMainPage = new StaffMainPage();
                    staffMainPage.start(staffMainStage);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.close();
                } else {
                    Alert wrongPasswordAlert = new Alert(Alert.AlertType.ERROR);
                    wrongPasswordAlert.setTitle("Wrong Password");
                    wrongPasswordAlert.setHeaderText("Incompatible password for staff id: " + usernameInput);
                    wrongPasswordAlert.setContentText("Please try again");
                    wrongPasswordAlert.showAndWait();
                    System.out.println("Error: rong password");
                    passwordTF.setText("");
                }

            }
        }
    }

    @FXML
    public void register(ActionEvent event) throws IOException {
        System.out.println("Operation: register");
        Alert registerOption = new Alert(Alert.AlertType.CONFIRMATION);
        registerOption.setTitle("Register");
        registerOption.setHeaderText("Confirmation");
        registerOption.setContentText("Register for student or staff?");
        ButtonType studentBtn = new ButtonType("Student", ButtonBar.ButtonData.YES);
        ButtonType staffBtn = new ButtonType("Staff", ButtonBar.ButtonData.NO);
        registerOption.getButtonTypes().setAll(studentBtn, staffBtn, ButtonType.CANCEL);

        ButtonType res = registerOption.showAndWait().get();
        if(res==ButtonType.CANCEL){
            // student registration logic
            System.out.println("Operation: cancel alert");
        }
        if(res==studentBtn){
            // student registration logic
            System.out.println("Operation: register for student");
            StudentRegisterPage studentRegisterPage = new StudentRegisterPage();
            studentRegisterPage.start(studentRegisterStage);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.close();
        }
        if(res==staffBtn){
            // staff registration logic
            System.out.println("Operation: register for staff");
            StaffRegisterPage staffRegisterPage = new StaffRegisterPage();
            staffRegisterPage.start(staffRegisterStage);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image mayaLogo = new Image("C://Users//CHEN CHING YEN//Downloads//mayaLogo3.png");
        mayaLogoiv.setImage(mayaLogo);
        statusCB.getItems().addAll(statusList);
        statusCB.setValue(statusList[0]);
    }
}