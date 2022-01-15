package com.example.prototype;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class StaffEditModulePageController implements Initializable {
    private String courseID, courseCode, courseName, staffID, staffName, activityType, specialRequirement, muetRequirementStr, dayString;
    private int credits, occ, day, startTime, endTime, target, actual, muetRequirement, dayint;
    private String newCourseID, newCourseCode, newCourseName, newCredit, newOcc, newStaffId, newStaffName, newActivityType, newDayString, newStartTimeString, newEndTimeString, newTargetString, newMuetRequirement, newSpecialRequirement;
    private int newDayint;
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
    @FXML
    private TextField credittf;

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

    public void editModule(ActionEvent event){
        newCourseCode = courseCodetf.getText().toLowerCase();
        newCourseName = courseNametf.getText().toLowerCase();
        newOcc = occtf.getText();
        newStaffId = staffIDtf.getText().toLowerCase();
        newStaffName = lecturerNametf.getText().toLowerCase();
        newActivityType = ((String)activityTypecb.getValue()).toLowerCase();
        newDayString = (String)daycb.getValue();
        newDayint = dayToString(newDayString);
        newStartTimeString = (String)startTimecb.getValue();
        newEndTimeString = (String)endTimecb.getValue();
        newCredit = credittf.getText();
        newTargetString = targettf.getText();
        newMuetRequirement = ((String)muetRequirementcb.getValue()).toLowerCase();
        newSpecialRequirement = ((String)specialRequirementcb.getValue()).toLowerCase();
        newCourseID = newCourseCode+"_"+newOcc+newActivityType.charAt(0);
        if(newCourseCode.equals("")||newCourseName.equals("")||newOcc.equals("")||newStaffId.equals("")||newStaffName.equals("")||newCredit.equals("")||newTargetString.equals("")){
            System.out.println("Error: incomplete information");
            Alert incompleteInformationAlert = new Alert(Alert.AlertType.ERROR);
            incompleteInformationAlert.setTitle("Error");
            incompleteInformationAlert.setHeaderText("Incomplete Information");
            incompleteInformationAlert.setContentText("Please provide all the information required");
            incompleteInformationAlert.showAndWait();
        }else{
            try{
                Integer.parseInt(newOcc);
                Integer.parseInt(newTargetString);
                Integer.parseInt(newCredit);
                // insert data
                System.out.println(newCourseID+" "+newCourseCode+" "+newCourseName+" "+newCredit+" "+newOcc+" "+newStaffId+" "+newStaffName+" "+newActivityType+" "+newDayString+" "+newStartTimeString+" "+newEndTimeString+" "+newTargetString+" "+newMuetRequirement+" "+newSpecialRequirement);
                Alert addConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                addConfirmationAlert.setTitle("Edit Module");
                addConfirmationAlert.setHeaderText("Are you sure want to edit this module?");
                addConfirmationAlert.setContentText("Course ID: "+newCourseID.toUpperCase()+"\n"+
                        "Course Code: "+newCourseCode.toUpperCase()+"\n"+
                        "Course Name: "+newCourseName.toUpperCase()+"\n"+
                        "Credit: "+newCredit+"\n"+
                        "Occurence: "+newOcc+"\n"+
                        "Staff ID: "+newStaffId.toUpperCase()+"\n"+
                        "Lecturer Name: "+newStaffName.toUpperCase()+"\n"+
                        "Activity Type: "+newActivityType.toUpperCase()+"\n"+
                        "Day: "+newDayString+"\n"+
                        "Start Time: "+newStartTimeString+"\n"+
                        "End Time: "+newEndTimeString+"\n"+
                        "Target: "+newTargetString+"\n"+
                        "MUET: "+newMuetRequirement.toUpperCase()+"\n"+
                        "Special Requirement: "+newSpecialRequirement.toUpperCase());
                if(addConfirmationAlert.showAndWait().get()== ButtonType.OK){

                    System.out.println("Operation: confirm to edit");
                    if(newMuetRequirement.equalsIgnoreCase("No Requirement")||newMuetRequirement.equalsIgnoreCase("0")){
                        newMuetRequirement = "null";
                    }else{
                        newMuetRequirement = "\""+newMuetRequirement+"\"";
                    }
                    if(newSpecialRequirement.equalsIgnoreCase("No Requirement")){
                        newSpecialRequirement = "null";
                    }else{
                        newSpecialRequirement = "\""+newSpecialRequirement+"\"";
                    }
                    // jdbc
                    try{
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
                        java.sql.Statement statement = conn.createStatement();
                        java.sql.Statement statement2 = conn.createStatement();
                        // UPDATE `prototype`.`courses_info` SET `occ` = '2' WHERE (`course_id` = 'abc1234_1t');
                        String sql = "UPDATE courses_info SET "
                                +"course_id = '"+newCourseID.toLowerCase()+"', "
                                +"course_code = '"+newCourseCode.toLowerCase()+"', "
                                +"course_name = '"+newCourseName.toLowerCase()+"', "
                                +"credits = "+newCredit+", "
                                +"occ = "+newOcc+", "
                                +"staff_id = '"+newStaffId.toLowerCase()+"', "
                                +"staff_name = '"+newStaffName.toLowerCase()+"', "
                                +"activity_type = '"+newActivityType+"', "
                                +"day = "+dayToString(newDayString)+", "
                                +"start_time = "+newStartTimeString+", "
                                +"end_time = "+(Integer.parseInt(newEndTimeString)-1)+", "
                                +"target = "+newTargetString+", "
                                +"muet_requirement = "+newMuetRequirement+", "
                                +"special_requirement = "+newSpecialRequirement
                                +" WHERE course_id = '"+SelectedModuleClass.courseCode.toLowerCase()+"_"+SelectedModuleClass.occ+SelectedModuleClass.activityType.toLowerCase().charAt(0)+"';";
                        System.out.println(sql);
                        statement.executeUpdate(sql);
                        sql = "ALTER TABLE `prototype`.`"+SelectedModuleClass.courseCode.toLowerCase()+"_"+SelectedModuleClass.occ+"`\n" +
                                "RENAME TO  `prototype`.`"+newCourseCode.toLowerCase()+"_"+newOcc.toLowerCase()+"`;";
                        System.out.println(sql);
                        statement.executeUpdate(sql);
                        //// special case
                        //// need to udpate the change of occ of a certain course in each registered student's table

                        sql = "SELECT matric_number FROM "+newCourseCode.toLowerCase()+"_"+newOcc.toLowerCase()+";";
                        System.out.println(sql);
                        ResultSet results = statement.executeQuery(sql);
                        String sql2 = "";
                        while(results.next()){
                            System.out.println("Registered student list:");
                            String matricNumberOutput = results.getString(1);
                            sql2 = "UPDATE `prototype`.`"+matricNumberOutput+"` SET occ = "+newOcc+", course_id = '"+newCourseID+"' WHERE course_id = '"+SelectedModuleClass.courseCode.toLowerCase()+"_"+SelectedModuleClass.occ+SelectedModuleClass.activityType.toLowerCase().charAt(0)+"';";
                            System.out.println(sql2);
                            statement2.executeUpdate(sql2);
                        }
                        //// close
                        conn.close();
                        System.out.println("Message: edited successfully");
                        Alert addedAlert = new Alert(Alert.AlertType.INFORMATION);
                        addedAlert.setTitle("Edited successfully");
                        addedAlert.setHeaderText("You have successfully edit a module");
                        addedAlert.setContentText("New course code: "+newCourseCode+"\nOccurence: "+newOcc);
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
        // jdbc start
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT * FROM courses_info WHERE course_code = '"+SelectedModuleClass.courseCode.toLowerCase()+"' AND occ = "+SelectedModuleClass.occ+" AND activity_type = '"+SelectedModuleClass.activityType.toLowerCase()+"';";
            ResultSet results = sqlStatement.executeQuery(sql);
            while (results.next()) {
                // start grabbing data

                // courseCodetf, courseNametf, occtf, staffIDtf, lecturerNametf, activityTypecb, daycb, startTimecb
                // endTimecb, targettf, muetRequirementcb, specialRequirementcb, credittf

                courseID = results.getString(1).toUpperCase();
                System.out.print(courseID+" ");

                courseCode = results.getString(2).toUpperCase();
                System.out.print(courseCode+" ");
                courseCodetf.setText(courseCode);

                courseName = results.getString(3).toUpperCase();
                System.out.print(courseName+" ");
                courseNametf.setText(courseName);

                credits = results.getInt(4);
                System.out.print(credits+" ");
                credittf.setText(String.valueOf(credits));

                occ = results.getInt(5);
                System.out.print(occ+" ");
                occtf.setText(String.valueOf(occ));

                staffID = results.getString(6).toUpperCase();
                System.out.print(staffID+" ");
                staffIDtf.setText(staffID);

                staffName = results.getString(7).toUpperCase();
                System.out.print(staffName+" ");
                lecturerNametf.setText(staffName);

                activityType = results.getString(8).toUpperCase();
                System.out.print(activityType+" ");
                activityTypecb.setValue(activityType);

                day = results.getInt(9);
                System.out.print(day+" ");
                dayString = stringToDay(day);
                daycb.setValue(dayString);

                startTime = results.getInt(10);
                System.out.print(startTime+" ");
                startTimecb.setValue(String.valueOf(startTime));

                endTime = results.getInt(11)+1;
                System.out.print(endTime+" ");
                endTimecb.setValue(String.valueOf(endTime));

                target = results.getInt(12);
                System.out.print(target+" ");
                targettf.setText(String.valueOf(target));

                actual = results.getInt(13);
                System.out.print(actual+" ");

                try{
                    muetRequirement = results.getInt(14);
                    muetRequirementStr = String.valueOf(muetRequirement);
                    if(muetRequirement!=0) System.out.print(muetRequirementStr+" ");
                    else muetRequirementStr = "No Requirement";
                }catch(Exception err){
                    muetRequirement = 0;
                    muetRequirementStr = "No Requirement";
                    System.out.print(muetRequirementStr+" ");
                }
                muetRequirementcb.setValue(muetRequirementStr);

                try{
                    specialRequirement = results.getString(15).toUpperCase();
                    if(specialRequirement.equals("")){
                        specialRequirement = "No Requirement";
                    }
                }catch(Exception err){
                    specialRequirement = "No Requirement";
                }
                System.out.print(specialRequirement+" ");
                specialRequirementcb.setValue(specialRequirement);

                System.out.println();
                // end grabbing data
            }
            if(actual == 0){
                System.out.println("Message: actual = 0 all information can be edited");
            }else{
                // courseCodetf, courseNametf, occtf, staffIDtf, lecturerNametf, activityTypecb, daycb, startTimecb
                // endTimecb, targettf,muetRequirementcb, specialRequirementcb, credittf
                System.out.println("Message: actual > 0 only few information can be edited");
                courseCodetf.setEditable(false);
                courseNametf.setEditable(false);
                activityTypecb.setDisable(true);
                daycb.setDisable(true);
                startTimecb.setDisable(true);
                endTimecb.setDisable(true);
                muetRequirementcb.setDisable(true);
                specialRequirementcb.setDisable(true);
                credittf.setEditable(false);
            }
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: class build failed");
            System.out.println(connectionError);
        }
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

    public String stringToDay(int day){
        switch (day){
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
        }
        return "";
    }
}
