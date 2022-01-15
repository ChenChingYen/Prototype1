package com.example.prototype;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ViewRegisteredStudentsPageController implements Initializable {
    @FXML
    private TableView studentInfoTable;
    @FXML
    private TableColumn<StudentClassRegistered, String> matricNumberc;
    @FXML
    private TableColumn<StudentClassRegistered, String> namec;
    // Labels
    @FXML
    private Label courseCodeLabel;
    @FXML
    private Label courseNameLabel;
    @FXML
    private Label occLabel;
    @FXML
    private Label staffLabel;
    @FXML
    private Label creditsLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label dayLabel;
    @FXML
    private Label activityTypeLabel;
    @FXML
    private Label targetLabel;
    @FXML
    private Label actualLabel;

    String matricNumber, name;

    private ObservableList<StudentClassRegistered> registeredStudentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set text for labels
        courseCodeLabel.setText(SelectedModuleClass.courseCode);
        courseNameLabel.setText(SelectedModuleClass.courseName);
        occLabel.setText(String.valueOf(SelectedModuleClass.occ));
        staffLabel.setText(SelectedModuleClass.staffName);
        creditsLabel.setText(SelectedModuleClass.activityType);
        timeLabel.setText(SelectedModuleClass.timeString);
        dayLabel.setText(SelectedModuleClass.day);
        activityTypeLabel.setText(SelectedModuleClass.activityType);
        targetLabel.setText(String.valueOf(SelectedModuleClass.target));
        actualLabel.setText(String.valueOf(SelectedModuleClass.actual));

        matricNumberc.setCellValueFactory(new PropertyValueFactory<>("matricNumber"));
        namec.setCellValueFactory(new PropertyValueFactory<>("name"));
        // jdbc start
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            sql = "SELECT * FROM "+SelectedModuleClass.courseCode+"_"+SelectedModuleClass.occ+";";
            ResultSet results = sqlStatement.executeQuery(sql);
            while (results.next()) {
                // start grabbing data
                matricNumber = results.getString(1).toUpperCase();
                System.out.print(matricNumber+" ");
                name = results.getString(2).toUpperCase();
                System.out.print(name+" ");
                System.out.println();
                // end grabbing data
                registeredStudentList.add(new StudentClassRegistered(matricNumber, name));
            }
            studentInfoTable.setItems(registeredStudentList);
            conn.close();
        } catch (Exception connectionError) {
            System.out.println("Error: class build failed");
            System.out.println(connectionError);
        }
    }

}
