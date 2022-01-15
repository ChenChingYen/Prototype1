package com.example.prototype;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class StudentRegisterPageController implements Initializable {
    @FXML
    private Stage loginStage = new Stage();
    @FXML
    private TextField siswamailTF;
    @FXML
    private TextField matricNumberTF;
    @FXML
    private TextField nameTF;
    @FXML
    private ChoiceBox programmeCB;
    @FXML
    private ToggleGroup gender;
    @FXML
    private ToggleGroup nationality;
    @FXML
    private TextField ICNumberTF;
    @FXML
    private ChoiceBox muetCB;
    @FXML
    private TextField passwordTF;
    @FXML
    private TextField confirmPasswordTF;

    String[] programmeList = {"Data Science",
            "Artificial Intelligence",
            "Information System",
            "Multimedia",
            "Computer System and Network",
            "Software Engineering"};

    String[] muetList = {"2", "3", "4", "5", "6"};

    String siswamailInput;
    String matricNumberInput;
    String nameInput;
    String programmeInput;
    String genderInput;
    String nationalityInput;
    String ICNumberInput;
    int muetInput;
    String passwordInput;
    String confirmPasswordInput;

    @FXML
    public void register(ActionEvent event){
        try{
            // siswamail
            siswamailInput = siswamailTF.getText();
            // matric number
            matricNumberInput = matricNumberTF.getText();
            // name
            nameInput = nameTF.getText();
            // programme
            programmeInput = (String)programmeCB.getValue();
            // gender
            genderInput = ((RadioButton) gender.getSelectedToggle()).getText();
            // nationality
            nationalityInput = ((RadioButton) nationality.getSelectedToggle()).getText();
            // ic number
            ICNumberInput = ICNumberTF.getText();
            // muet
            muetInput = Integer.parseInt((String)muetCB.getValue());
            // password
            passwordInput = passwordTF.getText();
            // confirm password
            confirmPasswordInput = confirmPasswordTF.getText();
            // authentication logic
            if(siswamailInput!=""&&matricNumberInput!=""&&nameInput!=""&&programmeInput!=""&&genderInput!=""&&passwordInput!=""&&confirmPasswordInput!="") {
                System.out.println("Message: valid information provided");
                if (!siswamailInput.substring(siswamailInput.length() - 16).equals("@siswa.um.edu.my")) { // u2102769@siswa.um.edu.my
                    System.out.println("Error: invalid siswamail");
                    siswamailTF.setText("");
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Maya 2.0");
                    alert1.setHeaderText("ERROR:");
                    alert1.setContentText("Invalid siswamail");
                    alert1.showAndWait();
                } else if (!passwordInput.equals(confirmPasswordInput)) {
                    System.out.println("Error: unmatched password");
                    passwordTF.setText("");
                    confirmPasswordTF.setText("");
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Maya 2.0");
                    alert2.setHeaderText("ERROR:");
                    alert2.setContentText("Unmatched password");
                    alert2.showAndWait();
                }else if(ICNumberInput.length()>12){
                    System.out.println("Error: invalid IC number");
                    ICNumberTF.setText("");
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setTitle("Maya 2.0");
                    alert3.setHeaderText("ERROR:");
                    alert3.setContentText("Invalid IC Number");
                    alert3.showAndWait();
                }else{
                    PasswordEncryption encryption = new PasswordEncryption();
                    String ePassword = encryption.encryption(passwordInput);
                    // jdbc
                    try{
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
                        java.sql.Statement statement = conn.createStatement();
                        String sql = "INSERT INTO student_info VALUES( "
                                +"\""+siswamailInput.toLowerCase()+"\", "
                                +"\""+matricNumberInput.toLowerCase()+"\", "
                                +"\""+nameInput.toLowerCase()+"\", "
                                +"\""+programmeInput.toLowerCase()+"\", "
                                +"\""+genderInput.toLowerCase()+"\", "
                                +"\""+nationalityInput.toLowerCase()+"\", "
                                +"\""+ICNumberInput.toLowerCase()+"\", "
                                +"\""+muetInput+"\", "+"0, "
                                +"\""+ePassword+"\", 0 );";
                        System.out.println(sql);
                        statement.executeUpdate(sql);
                        sql = "CREATE TABLE `prototype`.`"+matricNumberInput.toLowerCase()+"` (\n" +
                                "  `course_id` VARCHAR(10) NOT NULL,\n" +
                                "  `course_code` VARCHAR(10) NOT NULL,\n" +
                                "  `occ` INT NOT NULL,\n" +
                                "  `credit` INT NOT NULL,\n" +
                                "  `day` INT NOT NULL,\n" +
                                "  `start_time` INT NOT NULL,\n" +
                                "  `end_time` INT NOT NULL,\n" +
                                "  PRIMARY KEY (`course_id`),\n" +
                                "  UNIQUE INDEX `course_id_UNIQUE` (`course_id` ASC) VISIBLE);";
                        statement.executeUpdate(sql);
                        System.out.println(sql);
                        conn.close();
                        /*
                        CREATE TABLE `prototype`.`u2103701` (
                            `course_id` VARCHAR(10) NOT NULL,
                            `course_code` VARCHAR(10) NOT NULL,
                            `occ` INT NOT NULL,
                            `credit` INT NOT NULL,
                            `day` INT NOT NULL,
                            `start_time` INT NOT NULL,
                            `end_time` INT NOT NULL,
                            PRIMARY KEY (`course_id`),
                            UNIQUE INDEX `course_id_UNIQUE` (`course_id` ASC) VISIBLE);
                        */
                        System.out.println("Message: registered successfully");
//                        Alert registered = new Alert(Alert.AlertType.INFORMATION);
//                        registered.setTitle("Registered successfully");
//                        registered.setHeaderText("You have successfully created an account in MAYA 2.0!");
//                        registered.setContentText("Siswamail: "+siswamailInput.toUpperCase()+"\n"
//                                +"Matric Number: "+matricNumberInput.toUpperCase()+"\n"
//                                +"Name: "+nameInput.toUpperCase()+"\n"
//                                +"Programme: "+programmeInput.toUpperCase()+"\n"
//                                +"Gender: "+genderInput.toUpperCase()+"\n"
//                                +"Nationality: "+nationalityInput.toUpperCase()+"\n"
//                                +"IC Number: "+ICNumberInput.toUpperCase()+"\n"
//                                +"MUET: "+muetInput+"\n"
//                                +"Password: "+passwordInput+"\n");
//                        registered.showAndWait();
                    }catch(Exception databaseError){
                        System.out.println("Error: connection failed");
                        System.out.println(databaseError.getMessage());
                    }
                    // end jdbc
                    System.out.println("Student details:");
                    System.out.println("siswamail: "+siswamailInput);
                    System.out.println("matric number: "+matricNumberInput);
                    System.out.println("name: "+nameInput);
                    System.out.println("programme: "+programmeInput);
                    System.out.println("gender: "+genderInput);
                    System.out.println("nationality: "+nationalityInput);
                    System.out.println("IC Number: "+ICNumberInput);
                    System.out.println("muet: "+muetInput);
                    System.out.println("password: "+passwordInput);
                    System.out.println("confirm password: "+confirmPasswordInput);
                    // successful alert
                    Alert successfull = new Alert(Alert.AlertType.INFORMATION);
                    successfull.setTitle("Maya 2.0");
                    successfull.setHeaderText("Registered successfully");
                    successfull.setContentText("Student Name: "+nameInput.toUpperCase()+"\nMatric Number: "+matricNumberInput.toUpperCase());
                    successfull.showAndWait();
                    // back to login page
                    MainLoginPage loginPage = new MainLoginPage();
                    loginPage.start(loginStage);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.close();
                }
            }else{
                System.out.println("Error: please provide all information required");
                Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
                emptyAlert.setTitle("Maya 2.0");
                emptyAlert.setHeaderText("ERROR:");
                emptyAlert.setContentText("Please provide all information required");
                emptyAlert.showAndWait();
            }
        }catch (Exception e){
            System.out.println("Error: please provide valid information");
            Alert emptyAlert2 = new Alert(Alert.AlertType.ERROR);
            emptyAlert2.setTitle("Maya 2.0");
            emptyAlert2.setHeaderText("ERROR:");
            emptyAlert2.setContentText("Please provide valid information");
            emptyAlert2.showAndWait();
            System.out.println(e);
        }


    }
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        System.out.println("Operation: cancel");
        // back to login page
        MainLoginPage loginPage = new MainLoginPage();
        loginPage.start(loginStage);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programmeCB.getItems().addAll(programmeList);
        programmeCB.setValue(programmeList[0]);
        muetCB.getItems().addAll(muetList);
        muetCB.setValue(muetList[0]);
    }
}
