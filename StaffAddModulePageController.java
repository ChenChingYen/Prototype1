package com.example.prototype;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.EventListener;
import java.util.ResourceBundle;

public class StaffAddModulePageController implements Initializable {
    @FXML
    private TextField courseCodetf;
    @FXML
    private TextField courseNametf;
    @FXML
    private TextField occtf;
    @FXML
    private TextField staffIDtf;
    @FXML
    private TextField lecturerNametf;
    @FXML
    private TextField credittf;
    @FXML
    private ComboBox activityTypecb;
    @FXML
    private ComboBox daycb;
    @FXML
    private ComboBox startTimecb;
    @FXML
    private ComboBox endTimecb;
    @FXML
    private TextField targettf;
    @FXML
    private ComboBox muetRequirementcb;
    @FXML
    private ComboBox specialRequirementcb;

    String courseID, courseCode, courseName, staffId, lecturerName, activityType, specialRequirement, dayString, credit;
    String occ, startTime, endTime, target, muetRequirement;
    int dayint;

    String[] activityTypeArray = {"Lecture", "Tutorial", "Tutorial & Lab"};
    String[] dayStringArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    String[] timeArray = {"9", "10", "11", "12", "13", "14", "15", "16", "17"};
    String[] muetRequirementArray = {"No Requirement", "2", "3", "4", "5", "6"};
    String[] specialRequirementArray = {"No Requirement",
            "Data Science",
            "Artificial Intelligence",
            "Information System",
            "Multimedia",
            "Computer System and Network",
            "Software Engineering"};

    public void addModule(ActionEvent event){
        courseCode = courseCodetf.getText().toLowerCase();
        courseName = courseNametf.getText().toLowerCase();
        occ = occtf.getText();
        staffId = staffIDtf.getText().toLowerCase();
        lecturerName = lecturerNametf.getText().toLowerCase();
        activityType = ((String)activityTypecb.getValue()).toLowerCase();
        dayString = (String)daycb.getValue();
        dayint = dayToString(dayString);
        startTime = (String)startTimecb.getValue();
        endTime = (String)endTimecb.getValue();
        credit = credittf.getText();
        target = targettf.getText();
        muetRequirement = ((String)muetRequirementcb.getValue()).toLowerCase();
        specialRequirement = ((String)specialRequirementcb.getValue()).toLowerCase();
        courseID = courseCode+"_"+occ+activityType.charAt(0);
        if(courseCode.equals("")||courseName.equals("")||occ.equals("")||staffId.equals("")||lecturerName.equals("")||credit.equals("")||target.equals("")){
            System.out.println("Error: incomplete information");
            Alert incompleteInformationAlert = new Alert(Alert.AlertType.ERROR);
            incompleteInformationAlert.setTitle("Error");
            incompleteInformationAlert.setHeaderText("Incomplete Information");
            incompleteInformationAlert.setContentText("Please provide all the information required");
            incompleteInformationAlert.showAndWait();
        }else{
            boolean doesNotExist = notExist(courseCode, occ);
            if(doesNotExist){
                try{
                    Integer.parseInt(occ);
                    Integer.parseInt(target);
                    Integer.parseInt(credit);
                    // insert data

                    System.out.println(courseCode+" "+courseName+" "+credit+" "+occ+" "+staffId+" "+lecturerName+" "+activityType+" "+dayint+" "+startTime+" "+endTime+" "+target+" "+muetRequirement+" "+specialRequirement);
                    Alert addConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    addConfirmationAlert.setTitle("Add Module");
                    addConfirmationAlert.setHeaderText("Are you sure want to create this module?");
                    addConfirmationAlert.setContentText("Course ID: "+courseID.toUpperCase()+"\n"+
                            "Course Code: "+courseCode.toUpperCase()+"\n"+
                            "Course Name: "+courseName.toUpperCase()+"\n"+
                            "Credit: "+credit+"\n"+
                            "Occurence: "+occ+"\n"+
                            "Staff ID: "+staffId.toUpperCase()+"\n"+
                            "Lecturer Name: "+lecturerName.toUpperCase()+"\n"+
                            "Activity Type: "+activityType.toUpperCase()+"\n"+
                            "Day: "+dayString+"\n"+
                            "Start Time: "+startTime+"\n"+
                            "End Time: "+endTime+"\n"+
                            "Target: "+target+"\n"+
                            "MUET: "+muetRequirement.toUpperCase()+"\n"+
                            "Special Requirement: "+specialRequirement.toUpperCase());
                    if(addConfirmationAlert.showAndWait().get()== ButtonType.OK){
                        System.out.println("Operation: confirm to add");

                        if(muetRequirement.equalsIgnoreCase("No Requirement")){
                            muetRequirement = "null";
                        }else{
                            muetRequirement = "\""+muetRequirement+"\"";
                        }
                        if(specialRequirement.equalsIgnoreCase("No Requirement")){
                            specialRequirement = "null";
                        }else{
                            specialRequirement = "\""+specialRequirement+"\"";
                        }

                        // jdbc

                        try{
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
                            java.sql.Statement statement = conn.createStatement();
                            String sql = "INSERT INTO courses_info VALUES( "
                                    +"\""+courseID.toLowerCase()+"\", "
                                    +"\""+courseCode.toLowerCase()+"\", "
                                    +"\""+courseName.toLowerCase()+"\", "
                                    +credit+", "
                                    +occ+", "
                                    +"\""+staffId.toLowerCase()+"\", "
                                    +"\""+lecturerName.toLowerCase()+"\", "
                                    +"\""+activityType+"\", "
                                    +dayint+", "
                                    +startTime+", "
                                    +(Integer.parseInt(endTime)-1)+", "
                                    +target+", 0, "
                                    +muetRequirement+", "
                                    +specialRequirement+", 0 );";
                            System.out.println(sql);
                            statement.executeUpdate(sql);
                            sql = "CREATE TABLE prototype."+courseCode+"_"+occ+" (`matric_number` VARCHAR(10) NOT NULL,`student_name` VARCHAR(45) NOT NULL, PRIMARY KEY (`matric_number`));";
                            System.out.println(sql);
                            statement.executeUpdate(sql);
                            conn.close();
                            System.out.println("Message: added successfully");
                            Alert addedAlert = new Alert(Alert.AlertType.INFORMATION);
                            addedAlert.setTitle("Added successfully");
                            addedAlert.setHeaderText("You have successfully created a module");
                            addedAlert.setContentText("Course Code: "+courseCode+"\nOccurence: "+occ);
                            addedAlert.showAndWait();
                            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                            window.close();
                        }catch(Exception databaseError){
                            System.out.println("Error: connection failed");
                            System.out.println(databaseError.getMessage());
                        }

                    }

                }catch(Exception exception){
                    System.out.println("Error: invalid information");
                    Alert invalidInformationAlert = new Alert(Alert.AlertType.ERROR);
                    invalidInformationAlert.setTitle("Error");
                    invalidInformationAlert.setHeaderText("Invalid Information");
                    invalidInformationAlert.setContentText("Please provide correct information");
                    invalidInformationAlert.showAndWait();
                }
            }else{
                System.out.println("Error: the module has already exist");
                Alert alreadyExistAlert = new Alert(Alert.AlertType.ERROR);
                alreadyExistAlert.setTitle("Error");
                alreadyExistAlert.setHeaderText("Unable to create module");
                alreadyExistAlert.setContentText("The module "+courseCode+" occ"+occ+" has already exist");
                alreadyExistAlert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activityTypecb.getItems().addAll(activityTypeArray);
        activityTypecb.setValue(activityTypeArray[0]);
        daycb.getItems().addAll(dayStringArray);
        daycb.setValue(dayStringArray[0]);
        startTimecb.getItems().addAll(timeArray);
        startTimecb.setValue(timeArray[0]);
        endTimecb.getItems().addAll(timeArray);
        endTimecb.setValue(timeArray[0]);
        muetRequirementcb.getItems().addAll(muetRequirementArray);
        muetRequirementcb.setValue(muetRequirementArray[0]);
        specialRequirementcb.getItems().addAll(specialRequirementArray);
        specialRequirementcb.setValue(specialRequirementArray[0]);
    }

    public int dayToString(String dayString){
        switch (dayString){
            case "Monday":
                return 1;
            case "Tuesday":
                return 2;
            case "Wednesday":
                return 3;
            case "Thursday":
                return 4;
            case "Friday":
                return 5;
        }
        return 0;
    }

    public boolean notExist(String courseCode ,String occ){
        System.out.println("Operation: check already exist");
        Boolean doesNotExist = true;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT COUNT(course_code) FROM courses_info WHERE course_code = '"+courseCode+"' AND occ = "+occ+" AND activity_type = '"+activityType.toLowerCase()+"';";
            ResultSet results = sqlStatement.executeQuery(sql);
            while (results.next()) {
                int count = results.getInt(1);
                if (count > 0) {
                    doesNotExist = false;
                }
            }
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: connection error");
            System.out.println(connectionError);
        }
        return doesNotExist;
    }
}
