package com.example.prototype;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class StaffSearchModulesPageController implements Initializable {
    @FXML
    private Stage staffMainPageStage = new Stage();
    @FXML
    private Stage viewRegisteredStudentsPageStage = new Stage();
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

    public void viewMyCourse(){
        System.out.println("Operation: view my course");
        modulesClassList.clear();
        // jdbc start
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT * FROM courses_info WHERE staff_id LIKE '%"+staffObj.getStaffID()+"%';";
            ResultSet results = sqlStatement.executeQuery(sql);
            System.out.println("My courses:");
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

    public void viewRegisteredStudents(ActionEvent event) throws Exception{
        // set the selected course values into a class named selectedModuleClass
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

            System.out.println("Operation: view registered students in "+selectedCourse.getCourseCode()+" occ"+selectedCourse.getOcc());
            ViewRegisteredStudentsPage viewRegisteredStudentsPage = new ViewRegisteredStudentsPage();
            viewRegisteredStudentsPage.start(viewRegisteredStudentsPageStage);
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
    }

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
    }

}
