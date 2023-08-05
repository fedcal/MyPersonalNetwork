package com.mypersonalnetwork;

import com.mypersonalnetwork.allert.AllertDialogs;
import com.mypersonalnetwork.database.connection.DatabaseConnectionException;
import com.mypersonalnetwork.database.connection.DbConnection;
import com.mypersonalnetwork.logsystem.LogMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

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
    @FXML
    private void extractDB() throws IOException {
        DbConnection dbConnection;

        try {
            dbConnection = new DbConnection();
            dbConnection.exportData();
            LogMain.writeLog("Connessione al db per export data.\n", Level.INFO, HomeApplication.class.getName());
            AllertDialogs.viewPopUp("INFORMATION","Export data db OK!");
        } catch(ClassNotFoundException e) {
            AllertDialogs.viewPopUp("ERROR","Driver Database non trovato.");
            LogMain.writeLog(e.getClass()+" Driver Database non trovato.\n", Level.WARNING,HomeApplication.class.getName());
        } catch(InstantiationException e){
            AllertDialogs.viewPopUp("ERROR","Errore durante l'inizializzazione della connessione al database.");
            LogMain.writeLog(e.getClass()+" Errore durante l'inizializzazione della connessione al database.\n", Level.WARNING,HomeApplication.class.getName());
        } catch(IllegalAccessException e){
            AllertDialogs.viewPopUp("ERROR","Accesso al driver del database negato.");
            LogMain.writeLog(e.getClass()+" Accesso al driver del database negato.\n", Level.WARNING,HomeApplication.class.getName());
        }catch(SQLException e) {
            AllertDialogs.viewPopUp("ERROR","Impossibile eseguire la query di connessione.");
            LogMain.writeLog(e.getClass()+" Impossibile eseguire la query di connessione.\n", Level.WARNING,HomeApplication.class.getName());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (DatabaseConnectionException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}