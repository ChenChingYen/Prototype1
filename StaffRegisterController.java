package com.example.prototype;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;

public class StaffRegisterController {
    @FXML
    private final Stage loginStage = new Stage();
    @FXML
    private TextField UMmailTF;
    @FXML
    private TextField staffIDTF;
    @FXML
    private TextField nameTF;
    @FXML
    private ToggleGroup gender;
    @FXML
    private ToggleGroup nationality;
    @FXML
    private TextField ICNumberTF;
    @FXML
    private TextField passwordTF;
    @FXML
    private TextField confirmPasswordTF;

    String UMmailInput;
    String staffIDInput;
    String nameInput;
    String genderInput;
    String nationalityInput;
    String ICNumberInput;
    String passwordInput;
    String confirmPasswordInput;

    @FXML
    public void register(ActionEvent event){
        try{
            System.out.println("Register staff");
            // UMmail
            UMmailInput = UMmailTF.getText();
            // staffID
            staffIDInput = staffIDTF.getText();
            // name
            nameInput = nameTF.getText();
            // gender
            genderInput = ((RadioButton) gender.getSelectedToggle()).getText();
            // nationality
            nationalityInput = ((RadioButton) nationality.getSelectedToggle()).getText();
            // ic number
            ICNumberInput = ICNumberTF.getText();
            // password
            passwordInput = passwordTF.getText();
            // confirm password
            confirmPasswordInput = confirmPasswordTF.getText();
            // authentication logic
            if(UMmailInput!=""&&staffIDInput!=""&&nameInput!=""&&genderInput!=""&&passwordInput!=""&&confirmPasswordInput!="") {
                System.out.println("valid information provided");
//                if (!siswamailInput.substring(siswamailInput.length() - 16).equals("@siswa.um.edu.my")) { // u2102769@siswa.um.edu.my
//                    System.out.println("invalid siswamail");
//                    siswamailTF.setText("");
//                } else if (!passwordInput.equals(confirmPasswordInput)) {
                if(!passwordInput.equals(confirmPasswordInput)){
                    System.out.println("unmatched password. please try again");
                    passwordTF.setText("");
                    confirmPasswordTF.setText("");
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Maya 2.0");
                    alert1.setHeaderText("ERROR:");
                    alert1.setContentText("Unmatched password");
                    alert1.showAndWait();
                }else if(ICNumberInput.length()>12){
                    System.out.println("Invalid IC number");
                    ICNumberTF.setText("");
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Maya 2.0");
                    alert2.setHeaderText("ERROR:");
                    alert2.setContentText("Invalid IC Number");
                    alert2.showAndWait();
                }else{
                    PasswordEncryption encryption = new PasswordEncryption();
                    String ePassword = encryption.encryption(passwordInput);
                    // jdbc
                    try{
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
                        java.sql.Statement statement = conn.createStatement();
                        String sql = "INSERT INTO staff_info VALUES( "
                                +"\""+UMmailInput.toLowerCase()+"\", "
                                +"\""+staffIDInput.toLowerCase()+"\", "
                                +"\""+nameInput.toLowerCase()+"\", "
                                +"\""+genderInput.toLowerCase()+"\", "
                                +"\""+nationalityInput.toLowerCase()+"\", "
                                +"\""+ICNumberInput.toLowerCase()+"\", "
                                +"\""+ePassword+"\" )";
                        statement.executeUpdate(sql);
                        conn.close();
                        System.out.println("Message: Registered successfully");
//                        Alert registered = new Alert(Alert.AlertType.INFORMATION);
//                        registered.setTitle("Registered successfully");
//                        registered.setHeaderText("You have successfully created an account in MAYA 2.0!");
//                        registered.setContentText("UMmail: "+UMmailInput.toUpperCase()+"\n"
//                                +"Staff ID: "+staffIDInput.toUpperCase()+"\n"
//                                +"Name: "+nameInput.toUpperCase()+"\n"
//                                +"Gender: "+genderInput.toUpperCase()+"\n"
//                                +"Nationality: "+nationalityInput.toUpperCase()+"\n"
//                                +"IC Number: "+ICNumberInput.toUpperCase()+"\n"
//                                +"Password: "+passwordInput+"\n");
//                        registered.showAndWait();
                    }catch(Exception databaseError){
                        System.out.println("connection failed");
                    }
                    // end jdbc
                    System.out.println("registered successfully");
                    System.out.println("UM mail: "+UMmailInput);
                    System.out.println("staff ID: "+staffIDInput);
                    System.out.println("name: "+nameInput);
                    System.out.println("gender: "+genderInput);
                    System.out.println("nationality: "+nationalityInput);
                    System.out.println("IC Number: "+ICNumberInput);
                    System.out.println("password: "+passwordInput);
                    System.out.println("confirm password: "+confirmPasswordInput);
                    // successful alert
                    Alert successfull = new Alert(Alert.AlertType.INFORMATION);
                    successfull.setTitle("Maya 2.0");
                    successfull.setHeaderText("Registered successfully");
                    successfull.setContentText("Staff Name: "+nameInput.toUpperCase()+"\nStaff ID: "+staffIDInput.toUpperCase());
                    successfull.showAndWait();
                    // back to login page
                    MainLoginPage loginPage = new MainLoginPage();
                    loginPage.start(loginStage);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.close();
                }
            }else{
                System.out.println("Error: please provide all information required");
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Maya 2.0");
                alert3.setHeaderText("ERROR:");
                alert3.setContentText("Please provide all information required");
                alert3.showAndWait();
            }
        }catch (Exception e){
            System.out.println("Error: please provide valid information");
            System.out.println(e);
            Alert alert4 = new Alert(Alert.AlertType.ERROR);
            alert4.setTitle("Maya 2.0");
            alert4.setHeaderText("ERROR:");
            alert4.setContentText("Please provide valid information");
            alert4.showAndWait();
        }
    }
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        System.out.println("Cancel");
        // back to login page
        MainLoginPage loginPage = new MainLoginPage();
        loginPage.start(loginStage);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}
