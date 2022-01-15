package com.example.prototype;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StudentViewTimetablePage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentViewTimetablePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("View My Timetable | Maya 2.0");
        stage.setScene(scene);
        stage.setResizable(false);
        Image icon = new Image("C://Users//CHEN CHING YEN//IdeaProjects//umlogo1.png");
        stage.getIcons().add(icon);
        stage.show();
    }
}
