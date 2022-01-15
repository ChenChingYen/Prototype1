package com.example.prototype;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ViewRegisteredStudentsPage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewRegisteredStudentsPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("View Registered Students Maya 2.0");
        stage.setScene(scene);
        stage.setResizable(false);
        Image icon = new Image("C://Users//CHEN CHING YEN//IdeaProjects//umlogo1.png");
        stage.getIcons().add(icon);
        stage.show();
    }
}
