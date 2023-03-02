package com.example.skyunzipper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("welcome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 573, 436);
        stage.setScene(scene);
        stage.setTitle("Sky File Manager");
        stage.show();
    }

    public static void main(String[] args) {

        System.out.println("DONE! FILE UNZIPPED :)");
        launch();
    }

}