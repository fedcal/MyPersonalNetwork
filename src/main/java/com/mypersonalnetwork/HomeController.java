package com.mypersonalnetwork;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button addPeople;

    @FXML
    private Button addRelationsheep;

    @FXML
    private Button viewRelationsheep;

    @FXML
    private Button aboutPeople;

    @FXML
    private Button dbConnection;

    @FXML
    private Button exportDb;

    @FXML
    private Button setupDb;
    @FXML
    private Label welcomeText;

    @FXML
    private void addPeople() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-people-view.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("My Personal Netowork - Add People");
        stage.setResizable(false);
        stage.show();
    }
}