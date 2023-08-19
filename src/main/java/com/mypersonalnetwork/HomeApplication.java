package com.mypersonalnetwork;

import com.mypersonalnetwork.allert.AllertDialogs;
import com.mypersonalnetwork.database.connection.DatabaseConnectionException;
import com.mypersonalnetwork.database.connection.DbConnection;
import com.mypersonalnetwork.logsystem.LogMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

public class HomeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, DatabaseConnectionException {
        new LogMain();

        LogMain.writeLog("Start programming\n", Level.INFO,HomeApplication.class.getName());

        LogMain.writeLog("Connection to Database\n",Level.INFO,HomeApplication.class.getName());
        LogMain.writeLog("Start UI\n",Level.INFO,HomeApplication.class.getName());

        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("My Personal Network");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}