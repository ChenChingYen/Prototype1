package com.example.prototype;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentViewTimetablePageController implements Initializable {

    @FXML
    public Label l109;
    @FXML
    public Label l110;
    @FXML
    public Label l111;
    @FXML
    public Label l112;
    @FXML
    public Label l113;
    @FXML
    public Label l114;
    @FXML
    public Label l115;
    @FXML
    public Label l116;
    @FXML
    public Label l117;
    @FXML
    public Label l209;
    @FXML
    public Label l210;
    @FXML
    public Label l211;
    @FXML
    public Label l212;
    @FXML
    public Label l213;
    @FXML
    public Label l214;
    @FXML
    public Label l215;
    @FXML
    public Label l216;
    @FXML
    public Label l217;
    @FXML
    public Label l309;
    @FXML
    public Label l310;
    @FXML
    public Label l311;
    @FXML
    public Label l312;
    @FXML
    public Label l313;
    @FXML
    public Label l314;
    @FXML
    public Label l315;
    @FXML
    public Label l316;
    @FXML
    public Label l317;
    @FXML
    public Label l409;
    @FXML
    public Label l410;
    @FXML
    public Label l411;
    @FXML
    public Label l412;
    @FXML
    public Label l413;
    @FXML
    public Label l414;
    @FXML
    public Label l415;
    @FXML
    public Label l416;
    @FXML
    public Label l417;
    @FXML
    public Label l509;
    @FXML
    public Label l510;
    @FXML
    public Label l511;
    @FXML
    public Label l512;
    @FXML
    public Label l513;
    @FXML
    public Label l514;
    @FXML
    public Label l515;
    @FXML
    public Label l516;
    @FXML
    public Label l517;
    public ArrayList<Label> monList = new ArrayList<>();
    public ArrayList<Label> tueList = new ArrayList<>();
    public ArrayList<Label> wedList = new ArrayList<>();
    public ArrayList<Label> thuList = new ArrayList<>();
    public ArrayList<Label> friList = new ArrayList<>();
    public ArrayList<ArrayList<Label>> weekList = new ArrayList<>();

    String courseCode;
    int occ, day, startTime, endTime;

    @FXML
    private Stage studentMainPageStage = new Stage();

    StudentClass studentObj = new StudentClass();
    String matricNumber = studentObj.getMatricNumber();

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
        monList.add(l109);
        monList.add(l110);
        monList.add(l111);
        monList.add(l112);
        monList.add(l113);
        monList.add(l114);
        monList.add(l115);
        monList.add(l116);
        monList.add(l117);
        tueList.add(l209);
        tueList.add(l210);
        tueList.add(l211);
        tueList.add(l212);
        tueList.add(l213);
        tueList.add(l214);
        tueList.add(l215);
        tueList.add(l216);
        tueList.add(l217);
        wedList.add(l309);
        wedList.add(l310);
        wedList.add(l311);
        wedList.add(l312);
        wedList.add(l313);
        wedList.add(l314);
        wedList.add(l315);
        wedList.add(l316);
        wedList.add(l317);
        thuList.add(l409);
        thuList.add(l410);
        thuList.add(l411);
        thuList.add(l412);
        thuList.add(l413);
        thuList.add(l414);
        thuList.add(l415);
        thuList.add(l416);
        thuList.add(l417);
        friList.add(l509);
        friList.add(l510);
        friList.add(l511);
        friList.add(l512);
        friList.add(l513);
        friList.add(l514);
        friList.add(l515);
        friList.add(l516);
        friList.add(l517);

        weekList.add(monList);
        weekList.add(tueList);
        weekList.add(wedList);
        weekList.add(thuList);
        weekList.add(friList);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "20020407");
            java.sql.Statement sqlStatement = conn.createStatement();
            String sql = "";
            ResultSet results;
            System.out.println("Operation: display my timetable");
            int i = 0, j;
            for (ArrayList<Label> week: weekList) {
                int currentDay = i + 1;
                sql = "SELECT course_code, occ, day, start_time, end_time FROM " + matricNumber + " WHERE day = "+currentDay+";";
                j = 0;
                for (Label days: week) {
                    // check has class
                    int currentTime = j + 9;
                    results = sqlStatement.executeQuery(sql);
                    String display = "";
                    while (results.next()) {
                        // start grabbing data
                        courseCode = results.getString(1).toUpperCase();
//                        System.out.print(courseCode+" ");
                        occ = results.getInt(2);
//                        System.out.print(occ+" ");
                        day = results.getInt(3);
//                        System.out.print(day+" ");
                        startTime = results.getInt(4);
//                        System.out.print(startTime+" ");
                        endTime = results.getInt(5);
//                        System.out.println(endTime+" ");
                        // end check has class
                        if(currentTime>=startTime&&currentTime<=endTime) display = courseCode+" ("+occ+")";
                    }
//                    System.out.println(display);
                    days.setText(display);
                    j++;
                }
                i++;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

}
