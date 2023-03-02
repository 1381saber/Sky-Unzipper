package com.example.skyunzipper;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class welcomeController implements Initializable {

    @FXML
    private ImageView hello;


    @FXML
    private AnchorPane gif;


    @FXML
    private ImageView img1;

    @FXML
    void button1(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("app.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }


    Hyperlink link = new Hyperlink();

    @FXML
    void github(MouseEvent event) throws IOException {
        openUrlInBrowser("https://github.com/DS-01-02/ds-project-hello-zojaji-2");
    }

    private void openUrlInBrowser(String url) {
        Runtime runtime = Runtime.getRuntime();
        String[] args = {"osascript", "-e", "open location \"" + url + "\""};
        try {
            Process process = runtime.exec(args);
        } catch (IOException e) {

        }
    }

    @FXML
    void contact(MouseEvent event) throws IOException {

        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("contactUs.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(hello);
        translate.setDuration(Duration.millis(2500));
        translate.setCycleCount(1);
        translate.setByX(339);
        translate.play();
        RotateTransition fight = new RotateTransition();
        fight.setNode(hello);
        fight.setDuration(Duration.millis(1900));
        fight.setByAngle(360);
        fight.play();

    }
}
