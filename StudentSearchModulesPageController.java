package com.example.prototype;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class StudentSearchModulesPageController implements Initializable {
    @FXML
    private Stage studentMainPageStage = new Stage();
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

    ModuleClass tmp;

    StudentClass studentObject = new StudentClass();
    String matricNumber = studentObject.getMatricNumber();
    int studentMUET = studentObject.getMuet();
    String studentProgramme = studentObject.getProgramme();

    private ObservableList<ModuleClass> modulesClassList = FXCollections.observableArrayList();

    public void search(ActionEvent event){
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
//                if(courseCode.equalsIgnoreCase("wia2001")&&studentProgramme.equalsIgnoreCase("information system")){
//                    System.out.println();
//                    continue;
//                }else if(courseCode.equalsIgnoreCase("wib2001")&&!studentProgramme.equalsIgnoreCase("information system")){
//                    System.out.println();
//                    continue;
//                }
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
//                if( muetRequirement != 0 && muetRequirement != studentMUET ){
//                    System.out.println();
//                    continue;
//                }
                specialRequirement = results.getString(15);
//                if(specialRequirement!=null){
//                    if(!specialRequirement.equals(studentProgramme)){
//                        System.out.println();
//                        continue;
//                    }
//                }
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

    public void back(ActionEvent event) throws IOException {
        System.out.println("Operation: back");
        // back to login page
        StudentMainPage studentMainPage = new StudentMainPage();
        studentMainPage.start(studentMainPageStage);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(studentMUET==6)studentMUET=5;
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
//                if(courseCode.equalsIgnoreCase("wia2001")&&studentProgramme.equalsIgnoreCase("information system")){
//                    System.out.println();
//                    continue;
//                }else if(courseCode.equalsIgnoreCase("wib2001")&&!studentProgramme.equalsIgnoreCase("information system")){
//                    System.out.println();
//                    continue;
//                }
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
//                if( muetRequirement != 0 && muetRequirement != studentMUET ){
//                    System.out.println();
//                    continue;
//                }
                specialRequirement = results.getString(15);
//                if(specialRequirement!=null){
//                    if(!specialRequirement.equals(studentProgramme)){
//                        System.out.println();
//                        continue;
//                    }
//                }
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

    public static String switchDay(int dayNum){
        String dayStr = "";
        switch (dayNum){
            case 1:
                dayStr += "MON";
                break;

            case 2:
                dayStr += "TUE";
                break;
            case 3:
                dayStr += "WED";
                break;
            case 4:
                dayStr += "THU";
                break;
            case 5:
                dayStr += "FRI";
                break;
        }
        return dayStr;
    }
}
