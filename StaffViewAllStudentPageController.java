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

import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class StaffViewAllStudentPageController implements Initializable {

    @FXML
    private TableView searchResult;
    @FXML
    private TableColumn<StudentClassSR, String> siswamailc;
    @FXML
    private TableColumn<StudentClassSR, String> matricNumberc;
    @FXML
    private TableColumn<StudentClassSR, String> namec;
    @FXML
    private TableColumn<StudentClassSR, String> programmec;
    @FXML
    private TableColumn<StudentClassSR, String> genderc;
    @FXML
    private TableColumn<StudentClassSR, String> nationalityc;
    @FXML
    private TableColumn<StudentClassSR, String> icNumberc;
    @FXML
    private TableColumn<StudentClassSR, String> muetc;
    @FXML
    private TableColumn<StudentClassSR, String> creditsc;
    @FXML
    private TableColumn<StudentClassSR, String> statusc;
    @FXML
    private TextField searchtf;
    private ObservableList<StudentClassSR> studentClassSRList = FXCollections.observableArrayList();

    @FXML
    private Stage staffMainPageStage = new Stage();

    private String siswamail, matricNumber, name, programme, gender, nationality, statusStr, icNumber;
    private int muet, credits, status;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        siswamailc.setCellValueFactory(new PropertyValueFactory<>("siswamail"));
        matricNumberc.setCellValueFactory(new PropertyValueFactory<>("matricNumber"));
        namec.setCellValueFactory(new PropertyValueFactory<>("name"));
        programmec.setCellValueFactory(new PropertyValueFactory<>("programme"));
        genderc.setCellValueFactory(new PropertyValueFactory<>("gender"));
        nationalityc.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        icNumberc.setCellValueFactory(new PropertyValueFactory<>("icNumber"));
        muetc.setCellValueFactory(new PropertyValueFactory<>("muet"));
        creditsc.setCellValueFactory(new PropertyValueFactory<>("credits"));
        statusc.setCellValueFactory(new PropertyValueFactory<>("status"));
        // jdbc start
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT siswamail, matric_number, name, programme, gender, nationality, ic_number, muet, credit, status FROM prototype.student_info;";
            ResultSet results = sqlStatement.executeQuery(sql);
            System.out.println("Result: registered students:");
            while (results.next()) {
                // start grabbing data
                siswamail = results.getString(1).toUpperCase();
                System.out.print(siswamail+" ");
                matricNumber = results.getString(2).toUpperCase();
                System.out.print(matricNumber+" ");
                name = results.getString(3).toUpperCase();
                System.out.print(name+" ");
                programme = results.getString(4).toUpperCase();
                System.out.print(programme+" ");
                gender = results.getString(5).toUpperCase();
                System.out.print(gender+" ");
                nationality = results.getString(6).toUpperCase();
                System.out.print(nationality+" ");
                icNumber = results.getString(7);
                System.out.print(icNumber+" ");
                muet = results.getInt(8);
                System.out.print(muet+" ");
                credits = results.getInt(9);
                System.out.print(credits+" ");
                status = results.getInt(10);
                System.out.print(status+" ");
                if(status==0){
                    statusStr = "NOT ENROLLED";
                }else{
                    statusStr = "ENROLLED";
                }
                System.out.println();
                // end grabbing data
                studentClassSRList.add(new StudentClassSR(siswamail, matricNumber, name, programme, gender, nationality, icNumber, muet, credits, statusStr));
            }
            searchResult.setItems(studentClassSRList);
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: class build failed");
            System.out.println(connectionError);
        }
    }

    public void search(){
        studentClassSRList.clear();
        String keywords = searchtf.getText().toLowerCase();
        System.out.println("Operation: searching for "+keywords);
        // jdbc start
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT siswamail, matric_number, name, programme, gender, nationality, ic_number, muet, credit, status FROM student_info WHERE name LIKE '%"+keywords+"%' OR matric_number LIKE '%"+keywords+"%';";
            ResultSet results = sqlStatement.executeQuery(sql);
            System.out.println("Result: registered students:");
            while (results.next()) {
                // start grabbing data
                siswamail = results.getString(1).toUpperCase();
                System.out.print(siswamail+" ");
                matricNumber = results.getString(2).toUpperCase();
                System.out.print(matricNumber+" ");
                name = results.getString(3).toUpperCase();
                System.out.print(name+" ");
                programme = results.getString(4).toUpperCase();
                System.out.print(programme+" ");
                gender = results.getString(5).toUpperCase();
                System.out.print(gender+" ");
                nationality = results.getString(6).toUpperCase();
                System.out.print(nationality+" ");
                icNumber = results.getString(7);
                System.out.print(icNumber+" ");
                muet = results.getInt(8);
                System.out.print(muet+" ");
                credits = results.getInt(9);
                System.out.print(credits+" ");
                status = results.getInt(10);
                System.out.print(status+" ");
                if(status==0){
                    statusStr = "NOT ENROLLED";
                }else{
                    statusStr = "ENROLLED";
                }
                System.out.println();
                // end grabbing data
                studentClassSRList.add(new StudentClassSR(siswamail, matricNumber, name, programme, gender, nationality, icNumber, muet, credits, statusStr));
            }
            searchResult.setItems(studentClassSRList);
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: class build failed");
            System.out.println(connectionError);
        }
    }

    public void back(ActionEvent event) throws Exception{
        System.out.println("Operation: back");
        // back to login page
        StaffMainPage staffMainPage = new StaffMainPage();
        staffMainPage.start(staffMainPageStage);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}
