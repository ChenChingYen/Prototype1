package com.example.prototype;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class StaffModifyModulesPageController implements Initializable {

    @FXML
    private Stage staffMainPageStage = new Stage();
    @FXML
    private Stage staffAddModulePageStage = new Stage();
    @FXML
    private Stage staffEditModulePageStage = new Stage();
    @FXML
    private TextField searchTF;

    private String keywords;

    private String courseID;
    private String courseCode;
    private String courseName;
    private int credits;
    private int occ;
    private String staffID;
    private String staffName;
    private String activityType;
    private int day;
    private int startTime;
    private int endTime;
    private String timeString;
    private int target;
    private int actual;
    private int muetRequirement;
    private String specialRequirement;

    @FXML
    private TableView searchResult;
    @FXML
    private TableColumn<ModuleClass, String> moduleCodec;
    @FXML
    private TableColumn<ModuleClass, String> moduleNamec;
    @FXML
    private TableColumn<ModuleClass, String> creditsc;
    @FXML
    private TableColumn<ModuleClass, String> occc;
    @FXML
    private TableColumn<ModuleClass, String> activityTypec;
    @FXML
    private TableColumn<ModuleClass, String> lecturerc;
    @FXML
    private TableColumn<ModuleClass, String> dayc;
    @FXML
    private TableColumn<ModuleClass, String> timec;
    @FXML
    private TableColumn<ModuleClass, String> targetc;
    @FXML
    private TableColumn<ModuleClass, String> actualc;

    private ObservableList<ModuleClass> modulesClassList = FXCollections.observableArrayList();

    StaffClass staffObj = new StaffClass();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        moduleCodec.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        moduleNamec.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        creditsc.setCellValueFactory(new PropertyValueFactory<>("credit"));
        occc.setCellValueFactory(new PropertyValueFactory<>("occ"));
        lecturerc.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        activityTypec.setCellValueFactory(new PropertyValueFactory<>("activityType"));
        dayc.setCellValueFactory(new PropertyValueFactory<>("day"));
        timec.setCellValueFactory(new PropertyValueFactory<>("timeString"));
        targetc.setCellValueFactory(new PropertyValueFactory<>("target"));
        actualc.setCellValueFactory(new PropertyValueFactory<>("actual"));
        // jdbc start
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT * FROM courses_info;";
            ResultSet results = sqlStatement.executeQuery(sql);
            while (results.next()) {
                // start grabbing data
                courseID = results.getString(1).toUpperCase();
                System.out.print(courseID+" ");
                courseCode = results.getString(2).toUpperCase();
                System.out.print(courseCode+" ");
                courseName = results.getString(3).toUpperCase();
                System.out.print(courseName+" ");
                credits = results.getInt(4);
                System.out.print(credits+" ");
                occ = results.getInt(5);
                System.out.print(occ+" ");
                staffID = results.getString(6);
                System.out.print(staffID+" ");
                staffName = results.getString(7).toUpperCase();
                System.out.print(staffName+" ");
                activityType = results.getString(8).toUpperCase();
                System.out.print(activityType+" ");
                day = results.getInt(9);
                System.out.print(day+" ");
                startTime = results.getInt(10);
                System.out.print(startTime+" ");
                endTime = results.getInt(11);
                System.out.print(endTime+" ");
                timeString = startTime+"-"+(endTime+1);
                target = results.getInt(12);
                System.out.print(target+" ");
                actual = results.getInt(13);
                System.out.print(actual+" ");
                try{
                    muetRequirement = results.getInt(14);
                    System.out.print(muetRequirement+" ");
                }catch(Exception err){
                    muetRequirement = 0;
                    System.out.print(muetRequirement+" ");
                }
                specialRequirement = results.getString(15);
                System.out.print(specialRequirement+" ");
                System.out.println();
                // end grabbing data
                modulesClassList.add(new ModuleClass(courseCode, courseName, credits, occ,activityType, staffName,  switchDay(day), timeString, target, actual));
            }
            searchResult.setItems(modulesClassList);
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: class build failed");
            System.out.println(connectionError);
        }
    }

    public void search(){
        modulesClassList.clear();
        keywords = searchTF.getText().toLowerCase();
        System.out.println("Operation: searching for "+keywords);
        moduleCodec.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        moduleNamec.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        creditsc.setCellValueFactory(new PropertyValueFactory<>("credit"));
        occc.setCellValueFactory(new PropertyValueFactory<>("occ"));
        lecturerc.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        activityTypec.setCellValueFactory(new PropertyValueFactory<>("activityType"));
        dayc.setCellValueFactory(new PropertyValueFactory<>("day"));
        timec.setCellValueFactory(new PropertyValueFactory<>("timeString"));
        targetc.setCellValueFactory(new PropertyValueFactory<>("target"));
        actualc.setCellValueFactory(new PropertyValueFactory<>("actual"));
        // jdbc start
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT * FROM courses_info WHERE course_code LIKE '"+keywords+"%';";
            ResultSet results = sqlStatement.executeQuery(sql);
            System.out.println("Available courses:");
            while (results.next()) {
                // start grabbing data
                courseID = results.getString(1).toUpperCase();
                System.out.print(courseID+" ");
                courseCode = results.getString(2).toUpperCase();
                System.out.print(courseCode+" ");
                courseName = results.getString(3).toUpperCase();
                System.out.print(courseName+" ");
                credits = results.getInt(4);
                System.out.print(credits+" ");
                occ = results.getInt(5);
                System.out.print(occ+" ");
                staffID = results.getString(6);
                System.out.print(staffID+" ");
                staffName = results.getString(7).toUpperCase();
                System.out.print(staffName+" ");
                activityType = results.getString(8).toUpperCase();
                System.out.print(activityType+" ");
                day = results.getInt(9);
                System.out.print(day+" ");
                startTime = results.getInt(10);
                System.out.print(startTime+" ");
                endTime = results.getInt(11);
                System.out.print(endTime+" ");
                timeString = startTime+"-"+(endTime+1);
                target = results.getInt(12);
                System.out.print(target+" ");
                actual = results.getInt(13);
                System.out.print(actual+" ");
                try{
                    muetRequirement = results.getInt(14);
                    System.out.print(muetRequirement+" ");
                }catch(Exception err){
                    muetRequirement = 0;
                    System.out.print(muetRequirement+" ");
                }
                specialRequirement = results.getString(15);
                System.out.print(specialRequirement+" ");
                System.out.println();
                // end grabbing data
                modulesClassList.add(new ModuleClass(courseCode, courseName, credits, occ, activityType, staffName, switchDay(day), timeString, target, actual));
            }
            searchResult.setItems(modulesClassList);
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: class build failed");
            System.out.println(connectionError);
        }
    }

    public void modify(ActionEvent event){
        System.out.println("Operation: modify");
        try{
            ModuleClass selectedCourse = (ModuleClass) searchResult.getSelectionModel().getSelectedItem();
            SelectedModuleClass.courseCode = selectedCourse.getCourseCode();
            SelectedModuleClass.courseName = selectedCourse.getCourseName();
            SelectedModuleClass.credit = selectedCourse.getCredit();
            SelectedModuleClass.occ = selectedCourse.getOcc();
            SelectedModuleClass.activityType = selectedCourse.getActivityType();
            SelectedModuleClass.staffName = selectedCourse.getStaffName();
            SelectedModuleClass.day = selectedCourse.getDay();
            SelectedModuleClass.timeString = selectedCourse.getTimeString();
            SelectedModuleClass.target = selectedCourse.getTarget();
            SelectedModuleClass.actual = selectedCourse.getActual();
            System.out.println("Operation: modify "+SelectedModuleClass.courseCode+" occ"+SelectedModuleClass.occ);
            StaffEditModulePage staffEditModulePage = new StaffEditModulePage();
            staffEditModulePage.start(staffEditModulePageStage);
        }catch(Exception exception){
            System.out.println("Error: no module is selected");
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Error");
            noSelectionAlert.setHeaderText("No module was selected");
            noSelectionAlert.setContentText("Please try again");
            noSelectionAlert.showAndWait();
        }
    }

    public void add(ActionEvent event) throws Exception{
        System.out.println("Operation: add module");
        StaffAddModulePage staffAddModulePage = new StaffAddModulePage();
        staffAddModulePage.start(staffAddModulePageStage);
    }

    public void delete(ActionEvent event) throws Exception{
        System.out.println("Operation: delete");
//        // set the selected course values into a class named selectedModuleClass
        try{
            ModuleClass selectedCourse = (ModuleClass) searchResult.getSelectionModel().getSelectedItem();
            SelectedModuleClass.courseCode = selectedCourse.getCourseCode();
            SelectedModuleClass.courseName = selectedCourse.getCourseName();
            SelectedModuleClass.credit = selectedCourse.getCredit();
            SelectedModuleClass.occ = selectedCourse.getOcc();
            SelectedModuleClass.activityType = selectedCourse.getActivityType();
            SelectedModuleClass.staffName = selectedCourse.getStaffName();
            SelectedModuleClass.day = selectedCourse.getDay();
            SelectedModuleClass.timeString = selectedCourse.getTimeString();
            SelectedModuleClass.target = selectedCourse.getTarget();
            SelectedModuleClass.actual = selectedCourse.getActual();
            if(SelectedModuleClass.actual>0){
                System.out.println("Error: unable to delete");
                Alert unableToDeleteAlert = new Alert(Alert.AlertType.ERROR);
                unableToDeleteAlert.setTitle("Error");
                unableToDeleteAlert.setHeaderText("Unable  to delete "+SelectedModuleClass.courseCode+" occ"+SelectedModuleClass.occ);
                unableToDeleteAlert.setContentText("This module has been registered by students");
                unableToDeleteAlert.showAndWait();
            }else{
                System.out.println("Operation: delete "+selectedCourse.getCourseCode()+" occ"+selectedCourse.getOcc());
                Alert deleteConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                deleteConfirmationAlert.setTitle("Delete Module");
                deleteConfirmationAlert.setHeaderText("Delete Module "+SelectedModuleClass.courseCode+" occ"+SelectedModuleClass.occ);
                deleteConfirmationAlert.setContentText("Are you sure you want to delete this module?");

                if(deleteConfirmationAlert.showAndWait().get()== ButtonType.OK){
                    // confirm to delete
                    // DELETE FROM courses_info WHERE course_code = 'abc6666' AND occ = 1;
                    // DROP TABLE 'glt1018_1';
                    // jdbc start
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
                        java.sql.Statement sqlStatement = conn.createStatement();
                        String sql = "";
                        sql = "DELETE FROM courses_info WHERE course_code = '"+SelectedModuleClass.courseCode.toLowerCase()+"' AND occ = "+SelectedModuleClass.occ+";";
                        System.out.println(sql);
                        sqlStatement.executeUpdate(sql);
                        System.out.println("Operation: delete "+SelectedModuleClass.courseCode+" from database");
                        sql = "DROP TABLE prototype."+SelectedModuleClass.courseCode.toLowerCase()+"_"+SelectedModuleClass.occ+";";
                        System.out.println(sql);
                        sqlStatement.executeUpdate(sql);
                        System.out.println("Operation: delete table "+SelectedModuleClass.courseCode+"_"+SelectedModuleClass.occ+" from database");
                        conn.close();
                        update();
                    } catch (Exception connectionError) {
                        System.out.println("Error: class build failed");
                        System.out.println(connectionError);
                    }
                }
            }
        }catch(Exception exception){
            System.out.println("Error: no module is selected");
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Error");
            noSelectionAlert.setHeaderText("No module was selected");
            noSelectionAlert.setContentText("Please try again");
            noSelectionAlert.showAndWait();
        }
    }

    public void back(ActionEvent event) throws Exception{
        System.out.println("Operation: back");
        StaffMainPage staffMainPage = new StaffMainPage();
        staffMainPage.start(staffMainPageStage);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    } //

    public static String switchDay(int dayNum){
        String dayStr = "";
        switch (dayNum){
            case 1:
                dayStr += "MONDAY";
                break;

            case 2:
                dayStr += "TUESDAY";
                break;
            case 3:
                dayStr += "WEDNESDAY";
                break;
            case 4:
                dayStr += "THURSDAY";
                break;
            case 5:
                dayStr += "FRIDAY";
                break;
        }
        return dayStr;
    } //

    public void update(){
        modulesClassList.clear();
        System.out.println("Operation: refresh table");
        // jdbc start
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT * FROM courses_info;";
            ResultSet results = sqlStatement.executeQuery(sql);
            System.out.println("Available courses:");
            while (results.next()) {
                // start grabbing data
                courseID = results.getString(1).toUpperCase();
                System.out.print(courseID+" ");
                courseCode = results.getString(2).toUpperCase();
                System.out.print(courseCode+" ");
                courseName = results.getString(3).toUpperCase();
                System.out.print(courseName+" ");
                credits = results.getInt(4);
                System.out.print(credits+" ");
                occ = results.getInt(5);
                System.out.print(occ+" ");
                staffID = results.getString(6);
                System.out.print(staffID+" ");
                staffName = results.getString(7).toUpperCase();
                System.out.print(staffName+" ");
                activityType = results.getString(8).toUpperCase();
                System.out.print(activityType+" ");
                day = results.getInt(9);
                System.out.print(day+" ");
                startTime = results.getInt(10);
                System.out.print(startTime+" ");
                endTime = results.getInt(11);
                System.out.print(endTime+" ");
                timeString = startTime+"-"+(endTime+1);
                target = results.getInt(12);
                System.out.print(target+" ");
                actual = results.getInt(13);
                System.out.print(actual+" ");
                try{
                    muetRequirement = results.getInt(14);
                    System.out.print(muetRequirement+" ");
                }catch(Exception err){
                    muetRequirement = 0;
                    System.out.print(muetRequirement+" ");
                }
                specialRequirement = results.getString(15);
                System.out.print(specialRequirement+" ");
                System.out.println();
                // end grabbing data
                modulesClassList.add(new ModuleClass(courseCode, courseName, credits, occ, activityType, staffName, switchDay(day), timeString, target, actual));
            }
            searchResult.setItems(modulesClassList);
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: class build failed");
            System.out.println(connectionError);
        }
    }
}
