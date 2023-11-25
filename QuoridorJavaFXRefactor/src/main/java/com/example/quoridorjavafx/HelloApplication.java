package com.example.quoridorjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("controller.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 950);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void openWon(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("congratulations-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 683, 447);
            Stage stage = new Stage();
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public static void openInstructions(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("rule-book-controller.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 716, 513);
            Stage stage = new Stage();
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}