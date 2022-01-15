package com.example.prototype;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainLoginPage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome to Maya 2.0");
        stage.setScene(scene);
        stage.setResizable(false);

        Image icon = new Image("C://Users//CHEN CHING YEN//IdeaProjects//umlogo1.png"); // download um logo
        stage.getIcons().add(icon);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}