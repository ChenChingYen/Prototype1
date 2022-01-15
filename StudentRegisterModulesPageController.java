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

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentRegisterModulesPageController implements Initializable {
    @FXML
    private Stage studentMainPageStage = new Stage();
    @FXML
    private TextField searchTF;
    @FXML
    private TableView searchTable;
    @FXML
    private TableColumn<ModuleClassRS, String> scourseCodec;
    @FXML
    private TableColumn<ModuleClassRS, String> soccc;
    @FXML
    private TableColumn<ModuleClassRS, String> scourseNamec;
    @FXML
    private TableColumn<ModuleClassRS, String> screditc;
    @FXML
    private TableColumn<ModuleClassRS, String> stargetc;
    @FXML
    private TableColumn<ModuleClassRS, String> sactualc;
    //
    @FXML
    private TableView registeredTable;
    @FXML
    private TableColumn<ModuleClassR, String> rcourseCodec;
    @FXML
    private TableColumn<ModuleClassR, String> roccc;
    @FXML
    private Label currentCreditLabel;
    @FXML
    private Button confirmBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button dropBtn;
    int currentCredit = 0;
    //
    private String courseCode, specialRequirement, courseName;
    private int occ, credit, target, actual, muetRequirement;
    //
    private String rcourseCode;
    private int rocc;
    //
    String keywords;
    //
    String matricNumber = StudentClass.getMatricNumber();
    int studentMUET = StudentClass.getMuet();
    String studentProgramme = StudentClass.getProgramme();
    String studentName = StudentClass.getName();
    //
    private ObservableList<ModuleClassRS> modulesClassListRS = FXCollections.observableArrayList();
    private ObservableList<ModuleClassR> modulesClassListR = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(StudentClass.getStatus()==1){
            confirmBtn.setDisable(true);
            addBtn.setDisable(true);
            dropBtn.setDisable(true);
        }
        if(studentMUET==6)studentMUET = 5;
        scourseCodec.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        soccc.setCellValueFactory(new PropertyValueFactory<>("occ"));
        scourseNamec.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        screditc.setCellValueFactory(new PropertyValueFactory<>("credit"));
        stargetc.setCellValueFactory(new PropertyValueFactory<>("target"));
        sactualc.setCellValueFactory(new PropertyValueFactory<>("actual"));
        // jdbc start
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT course_code, occ, course_name, credits, target, actual, muet_requirement, special_requirement\n" +
                    "FROM courses_info\n" +
                    "GROUP BY course_code, occ;";
            ResultSet results = sqlStatement.executeQuery(sql);
//            System.out.println("Available courses:");
            while (results.next()) {
                // start grabbing data
                courseCode = results.getString(1).toUpperCase();
//                System.out.print(courseCode+" ");
                if(courseCode.equalsIgnoreCase("wia2001")&&studentProgramme.equalsIgnoreCase("information system")){
//                    System.out.println();
                    continue;
                }else if(courseCode.equalsIgnoreCase("wib2001")&&!studentProgramme.equalsIgnoreCase("information system")){
//                    System.out.println();
                    continue;
                }
                occ = results.getInt(2);
//                System.out.print(occ+" ");
                courseName = results.getString(3).toUpperCase();
//                System.out.print(courseName+" ");
                credit = results.getInt(4);
//                System.out.print(credit+" ");
                target = results.getInt(5);
//                System.out.print(target+" ");
                actual = results.getInt(6);
//                System.out.print(actual+" ");
                try{
                    muetRequirement = results.getInt(7);
//                    System.out.print(muetRequirement+" ");
                }catch(Exception err){
                    muetRequirement = 0;
//                    System.out.print(muetRequirement+" ");
                }
                if( muetRequirement != 0 && muetRequirement != studentMUET )continue;
                specialRequirement = results.getString(8);
//                System.out.print(specialRequirement+" ");
                if(specialRequirement!=null){
                    if(!specialRequirement.equals(studentProgramme))continue;
                }
//                System.out.println();
                // end grabbing data
                modulesClassListRS.add(new ModuleClassRS(courseCode, occ, courseName, credit, target, actual));
            }
            searchTable.setItems(modulesClassListRS);
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: class build failed");
            System.out.println(connectionError);
        }
        // registered courses
        rcourseCodec.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        roccc.setCellValueFactory(new PropertyValueFactory<>("occ"));
        // jdbc start
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT course_code, occ FROM "+matricNumber+" GROUP BY course_code;";
            ResultSet results = sqlStatement.executeQuery(sql);
            while (results.next()) {
                // start grabbing data
                rcourseCode = results.getString(1).toUpperCase();
                rocc = results.getInt(2);
                // end grabbing data
                modulesClassListR.add(new ModuleClassR(rcourseCode, rocc));
            }
            registeredTable.setItems(modulesClassListR);
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: class build failed");
            System.out.println(connectionError);
        }
        // get current credit
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT DISTINCT course_code, credit FROM "+matricNumber+";";
//            System.out.println(sql);
            ResultSet results = sqlStatement.executeQuery(sql);
            while (results.next()) {
                // start grabbing data
                currentCredit += results.getInt(2);
            }
//            System.out.println("current credit: "+currentCredit);
            currentCreditLabel.setText(String.valueOf(currentCredit));
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

    public void search(ActionEvent event){
        System.out.println("Operation: search modules");
        modulesClassListRS.clear();
        keywords = searchTF.getText().toLowerCase();
        // jdbc start
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT course_code, occ, course_name, credits, target, actual, muet_requirement, special_requirement\n" +
                    "FROM courses_info WHERE course_code LIKE \'"+keywords+"%\'\n" +
                    "GROUP BY course_code, occ;";
            ResultSet results = sqlStatement.executeQuery(sql);
//            System.out.println("Available courses:");
            while (results.next()) {
                // start grabbing data
                courseCode = results.getString(1).toUpperCase();
//                System.out.print(courseCode+" ");
                if(courseCode.equalsIgnoreCase("wia2001")&&studentProgramme.equalsIgnoreCase("information system")){
//                    System.out.println();
                    continue;
                }else if(courseCode.equalsIgnoreCase("wib2001")&&!studentProgramme.equalsIgnoreCase("information system")){
//                    System.out.println();
                    continue;
                }
                occ = results.getInt(2);
//                System.out.print(occ+" ");
                courseName = results.getString(3).toUpperCase();
//                System.out.print(courseName+" ");
                credit = results.getInt(4);
//                System.out.print(credit+" ");
                target = results.getInt(5);
//                System.out.print(target+" ");
                actual = results.getInt(6);
//                System.out.print(actual+" ");
                try{
                    muetRequirement = results.getInt(7);
//                    System.out.print(muetRequirement+" ");
                }catch(Exception err){
                    muetRequirement = 0;
//                    System.out.print(muetRequirement+" ");
                }
                if( muetRequirement != 0 && muetRequirement != studentMUET )continue;
                specialRequirement = results.getString(8);
//                System.out.print(specialRequirement+" ");
                if(specialRequirement!=null){
                    if(!specialRequirement.equals(studentProgramme))continue;
                }
//                System.out.println();
                // end grabbing data
                modulesClassListRS.add(new ModuleClassRS(courseCode, occ, courseName, credit, target, actual));
            }
            searchTable.setItems(modulesClassListRS);
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: class build failed");
            System.out.println(connectionError);
        }
    }

    public void add(){
        System.out.println("Operation: add");
        ModuleClassRS selectedCourse = (ModuleClassRS) searchTable.getSelectionModel().getSelectedItem();
        String selectedCourseCode = selectedCourse.getCourseCode();
        int selectedOcc = selectedCourse.getOcc();
        System.out.println("Operation: trying to add "+selectedCourseCode+" occ: "+selectedOcc+" into temporary table");
        Boolean underTarget = checkActual(selectedCourseCode, selectedOcc);
        Boolean hasNotTaken = checkHasTaken(selectedCourseCode);
        if(underTarget&&hasNotTaken){
            // add items into temporary table
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
                java.sql.Statement sqlStatement = conn.createStatement();
                String sql = "";
                sql = "SELECT course_id, course_code, occ, credits, day, start_time, end_time\n" +
                        "FROM courses_info\n" +
                        "WHERE course_code =  \'"+selectedCourseCode+"\' AND occ = "+selectedOcc+";";
//            System.out.println(sql);
                ResultSet results = sqlStatement.executeQuery(sql);
                while (results.next()) {
                    sqlStatement = conn.createStatement();
                    // start grabbing data
                    String courseID = results.getString(1);
                    courseCode = results.getString(2);
                    occ = results.getInt(3);
                    credit = results.getInt(4);
                    int day = results.getInt(5);
                    int startTime = results.getInt(6);
                    int endTime = results.getInt(7);
                    sql = "INSERT INTO "+matricNumber+" VALUES (\'"+courseID+"\', \'"+courseCode+"\', "+occ+", "+credit+", "+day+", "+startTime+", "+endTime+");";
//                System.out.println(sql);
                    sqlStatement.executeUpdate(sql);
//                System.out.println("continue");
                }
                conn.close();
            } catch (Exception connectionError) {
                System.out.println("Error: connection error");
                System.out.println(connectionError);
            }
            // end
            update();
        }else{
            System.out.println("Error: adding failed");
        }

    }

    public void drop(){
        System.out.println("Operation: drop");
        ModuleClassR selectedCourse = (ModuleClassR) registeredTable.getSelectionModel().getSelectedItem();
        String selectedCourseCode = selectedCourse.getCourseCode();
        int selectedOcc = selectedCourse.getOcc();
        System.out.println("Message: drop "+selectedCourseCode+" "+selectedOcc+" from temporary table");
        // drop items from temporary table
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "DELETE FROM "+matricNumber+" WHERE course_code = '"+selectedCourseCode.toLowerCase()+"';";
//            System.out.println(sql);
            sqlStatement.executeUpdate(sql);
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: connection error");
            System.out.println(connectionError);
        }
        // end
        update();
    }

    public void confirm(){
        System.out.println("Operation: confirm");
        boolean validCredit = checkTotalCredits();
        boolean noClashing = checkClashing();
        if(validCredit&&noClashing){
            System.out.println("Message: modules registration succeed");
            // UPDATE `prototype`.`student_info` SET `status` = '1' WHERE (`matric_number` = 'u2102769');
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
                java.sql.Statement sqlStatement = conn.createStatement();
                String sql = "";
                sql = "UPDATE student_info SET status = 1 WHERE matric_number = '"+matricNumber+"';";
                sqlStatement.executeUpdate(sql);
            } catch (Exception connectionError) {
                System.out.println("Error: connection error");
                System.out.println(connectionError);
            }
            Alert lastConfirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            lastConfirmAlert.setTitle("Maya 2.0");
            lastConfirmAlert.setHeaderText("Register confirmation");
            lastConfirmAlert.setContentText("You are not allowed to modify your courses after confirmed");
            if(lastConfirmAlert.showAndWait().get()==ButtonType.OK){
                // confirm to register
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
                    java.sql.Statement sqlStatement = conn.createStatement();
                    String sql = "";
                    sql = "SELECT course_code, occ FROM "+matricNumber+" GROUP BY course_code, occ;";
                    ResultSet results = sqlStatement.executeQuery(sql);
                    int actualOutput = 0;
                    String tableName;
                    ArrayList<String> tableNames = new ArrayList<>();
                    while (results.next()) {
                        // start grabbing data
                        courseCode = results.getString(1);
                        occ = results.getInt(2);
                        tableName = courseCode+"_"+occ;
                        tableNames.add(tableName);
                        System.out.println("table name: "+tableName);
                        // set matric number and name
                        sqlStatement = conn.createStatement();
                        sql = "INSERT INTO "+tableName+" VALUES ('"+matricNumber+"', '"+studentName+"');";
                        System.out.println(sql);
                        sqlStatement.executeUpdate(sql);
                        System.out.println("Operation: student "+studentName+" was inserted into the table "+tableName);
                    }
                    conn.close();
                    for(int i=0; i<tableNames.size(); i++) {
                        System.out.println(tableNames.get(i));
                    }
                    java.sql.Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
                    java.sql.Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
                    java.sql.Statement sqlStatement1 = conn1.createStatement();
                    java.sql.Statement sqlStatement2 = conn2.createStatement();
                    for(int i=0; i<tableNames.size(); i++){
                        // get actual
                        String tmp = tableNames.get(i);
                        courseCode = tmp.substring(0, 7);
                        occ = Integer.parseInt(tmp.substring(8));
                        System.out.println("Operation: getting actual value for "+courseCode+" occ"+occ);
                        sql = "SELECT actual FROM courses_info WHERE course_code = '"+courseCode+"' AND occ = "+occ+" GROUP BY course_code, occ;";
                        System.out.println(sql);
                        ResultSet actual = sqlStatement1.executeQuery(sql);
                        while(actual.next()){
                            actualOutput = actual.getInt(1);
                            System.out.println("Message: the actual value of "+courseCode+" = "+actualOutput);
                        }
                        System.out.println("Operation: the actual for "+courseCode+" occ"+occ+" was updated from "+actualOutput+" to "+(actualOutput+1));
                        actualOutput++;
                        // update actual
                        sql = "UPDATE courses_info SET actual = "+actualOutput+" WHERE course_code = '"+courseCode+"' AND occ = "+occ+";";
                        System.out.println(sql);
                        sqlStatement2.executeUpdate(sql);
                    }
                    conn1.close();
                    conn2.close();
                } catch (Exception connectionError) {
                    System.out.println("Error: class build failed");
                    System.out.println(connectionError);
                }
                StudentClass.setStatus(1);
                confirmBtn.setDisable(true);
                Alert successfull = new Alert(Alert.AlertType.INFORMATION);
                successfull.setTitle("Maya 2.0");
                successfull.setHeaderText("Registered successfully");
                successfull.setContentText("Student "+StudentClass.getName().toUpperCase()+" has successfully registered for modules");
                successfull.showAndWait();
            }
        }else{
            System.out.println("Error: modules registration failed");
        }
    }

    public boolean checkActual(String checkCourseCode, int checkOcc){
        System.out.println("Operation: check actual");
        Boolean underTarget = true;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT target, actual FROM courses_info WHERE course_code = '"+checkCourseCode+"' AND occ = "+checkOcc+" GROUP BY course_code, occ;";
//            System.out.println(sql);
            ResultSet results = sqlStatement.executeQuery(sql);
            while (results.next()) {
                int target = results.getInt(1);
                int actual = results.getInt(2);
                if(actual>=target){
                    underTarget = false;
                    System.out.println("Error: the course "+checkCourseCode+" occ"+checkOcc+" is full");
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Maya 2.0");
                    errorAlert.setHeaderText("Adding failed");
                    errorAlert.setContentText("The course "+checkCourseCode+" occ"+checkOcc+" is full");
                    errorAlert.showAndWait();
                }
            }
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: connection error");
            System.out.println(connectionError);
        }
        return underTarget;
    } //

    public boolean checkHasTaken(String checkCourseCode) {
        System.out.println("Operation: check has taken");
        Boolean hasNotTaken = true;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT COUNT(course_code) FROM " + matricNumber + " WHERE course_code = '" + checkCourseCode + "';";
//            System.out.println(sql);
            ResultSet results = sqlStatement.executeQuery(sql);
            while (results.next()) {
                int count = results.getInt(1);
                if (count > 0) {
                    hasNotTaken = false;
                    System.out.println("Error: course "+checkCourseCode+" has been taken");
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Maya 2.0");
                    errorAlert.setHeaderText("Adding failed");
                    errorAlert.setContentText("You have taken "+checkCourseCode);
                    errorAlert.showAndWait();
                }
            }
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: connection error");
            System.out.println(connectionError);
        }
        return hasNotTaken;
    } //

    public boolean checkClashing(){
        System.out.println("Operation: check clashing");
        //SELECT course_code, occ, day, start_time, end_time FROM u2102769;
        String courseCode;
        int occ, day, startTime, endTime;
        int curentCheckingIndex = 1;
        ModuleClasstmp tmpObj;
        ArrayList<ModuleClasstmp> dataArrayList = new ArrayList<>();
        Boolean noClashing = true;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT course_code, occ, day, start_time, end_time FROM "+matricNumber+";";
            ResultSet results = sqlStatement.executeQuery(sql);
            while (results.next()) {
                courseCode = results.getString(1);
                occ = results.getInt(2);
                day = results.getInt(3);
                startTime = results.getInt(4);
                endTime = results.getInt(5);
                tmpObj = new ModuleClasstmp(courseCode, occ, day, startTime, endTime);
                dataArrayList.add(tmpObj);
            }
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: connection error");
            System.out.println(connectionError);
        }
        // check clashing of time
//            System.out.println("testing: printing arraylist");
//            for(ModuleClasstmp x: dataArrayList){
//                System.out.println(x.getCourseCode()+" "+x.getOcc()+" "+x.getDay()+" "+x.getStartTime()+" "+x.getEndTime());
//            }
        System.out.println("testing: comparing between courses");
        String courseCode1, courseCode2;
        int day1, startTime1, endTime1, day2, startTime2, endTime2;
        for(int i=0; i<dataArrayList.size(); i++){
            for(int j=curentCheckingIndex; j<dataArrayList.size(); j++){
                System.out.print("comparing: ");
                courseCode1 = dataArrayList.get(i).getCourseCode();
                day1 = dataArrayList.get(i).getDay();
                startTime1 = dataArrayList.get(i).getStartTime();
                endTime1 = dataArrayList.get(i).getEndTime();
                courseCode2 = dataArrayList.get(j).getCourseCode();
                day2 = dataArrayList.get(j).getDay();
                startTime2 = dataArrayList.get(j).getStartTime();
                endTime2 = dataArrayList.get(j).getEndTime();
                System.out.println(courseCode1+" day: "+day1+" starttime: "+startTime1+" endtime: "+endTime1+
                        " and "+courseCode2+" day: "+day2+" starttime: "+startTime2+" endtime: "+endTime2);
                if(day1 == day2){ // check clashing when day 1 = day 2
                    for(int k=startTime1; k<=endTime1; k++){
                        if(k>=startTime2&&k<=endTime2){
                            System.out.println("Error: clashing between "+courseCode1+" and "+courseCode2);
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Maya 2.0");
                            errorAlert.setHeaderText("Registration failed");
                            errorAlert.setContentText("There is a clash between "+courseCode1+" and "+courseCode2);
                            errorAlert.showAndWait();
                            return false;
                        }
                    }
                }
            }
            curentCheckingIndex++;
        }
        System.out.println("message: no clashing occurs");
        return true;
    }

    public void update(){
        System.out.println("Operation: update my table view");
        modulesClassListR.clear();
        // registered courses
        rcourseCodec.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        roccc.setCellValueFactory(new PropertyValueFactory<>("occ"));
        // jdbc start
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT course_code, occ FROM "+matricNumber+" GROUP BY course_code;";
            ResultSet results = sqlStatement.executeQuery(sql);
            while (results.next()) {
                // start grabbing data
                rcourseCode = results.getString(1).toUpperCase();
                rocc = results.getInt(2);
                // end grabbing data
                modulesClassListR.add(new ModuleClassR(rcourseCode, rocc));
            }
            registeredTable.setItems(modulesClassListR);
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: class build failed");
            System.out.println(connectionError);
        }
        // get current credit
        currentCredit = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT DISTINCT course_code, credit FROM "+matricNumber+";";
//            System.out.println(sql);
            ResultSet results = sqlStatement.executeQuery(sql);
            while (results.next()) {
                // start grabbing data
                currentCredit += results.getInt(2);
            }
            System.out.println("current credit: "+currentCredit);
            currentCreditLabel.setText(String.valueOf(currentCredit));
            StudentClass.setCredit(currentCredit);
            sqlStatement = conn.createStatement();
            sql = "UPDATE student_info SET credit = "+currentCredit+" WHERE matric_number = '"+matricNumber+"';";
//            System.out.println(sql);
            sqlStatement.executeUpdate(sql);
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: class build failed");
            System.out.println(connectionError);
        }
    } //

    public boolean checkTotalCredits(){
        System.out.println("Operation: check my total credits");
        if(currentCredit<16||currentCredit>22){
            if(currentCredit<16){
                System.out.println("Error: not enough credit to register");
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Maya 2.0");
                errorAlert.setHeaderText("Registration failed");
                errorAlert.setContentText("You credit hours are lower than 16");
                errorAlert.showAndWait();
            }else{
                System.out.println("Error: exceed credit limit to register");
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Maya 2.0");
                errorAlert.setHeaderText("Registration failed");
                errorAlert.setContentText("You credit hours have exceed 22");
                errorAlert.showAndWait();
            }
            return false;
        }
        return true;
    } //
}
