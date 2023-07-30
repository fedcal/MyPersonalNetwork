package com.mypersonalnetwork;

import com.mypersonalnetwork.database.connection.DatabaseConnectionException;
import com.mypersonalnetwork.database.connection.DbConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;

public class HomeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, DatabaseConnectionException {

        try {
            DbConnection dbConnection = new DbConnection();
        } catch(ClassNotFoundException e) {
            throw new DatabaseConnectionException("Attenzione!!! Driver non trovato: " + e.getMessage());
        } catch(InstantiationException e){
            throw new DatabaseConnectionException("Attenzione!!! Errore durante l'instanzazione: " + e.getMessage());
        } catch(IllegalAccessException e){
            throw new DatabaseConnectionException("Attenzione!!! Accesso al driver negato: " + e.getMessage());
        }catch(SQLException e) {
            throw new DatabaseConnectionException("Attenzione!!! SQLException: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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