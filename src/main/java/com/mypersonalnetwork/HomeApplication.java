package com.mypersonalnetwork;

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
        try {
            DbConnection dbConnection = new DbConnection();
        } catch(ClassNotFoundException e) {
            LogMain.writeLog(e.getClass()+" Driver Database non trovato.\n", Level.WARNING,HomeApplication.class.getName());
        } catch(InstantiationException e){
            LogMain.writeLog(e.getClass()+" Errore durante l'inizializzazione della connessione al database.\n", Level.WARNING,HomeApplication.class.getName());
        } catch(IllegalAccessException e){
            LogMain.writeLog(e.getClass()+" Accesso al driver del database negato.\n", Level.WARNING,HomeApplication.class.getName());
        }catch(SQLException e) {
            LogMain.writeLog(e.getClass()+" Impossibile eseguire la query di connessione.\n", Level.WARNING,HomeApplication.class.getName());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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