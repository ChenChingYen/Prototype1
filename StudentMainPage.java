package com.example.prototype;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class StudentMainPage extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentMainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Student Home Page | Maya 2.0");
        stage.setScene(scene);
        stage.setResizable(false);
        Image icon = new Image("C://Users//CHEN CHING YEN//IdeaProjects//umlogo1.png");
        stage.getIcons().add(icon);
        stage.show();
    }
}
