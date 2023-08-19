package com.mypersonalnetwork.controller;

import com.mypersonalnetwork.HomeApplication;
import com.mypersonalnetwork.allert.AllertDialogs;
import com.mypersonalnetwork.logsystem.LogMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class DatabaseSettingsControllerView implements Initializable {
    @FXML
    public TextField databaseName;
    @FXML
    public TextField userName;
    @FXML
    public TextField password;
    @FXML
    public TextField portNumber;
    @FXML
    public TextField serverName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            LogMain.writeLog("Open DbSettings\n", Level.INFO, HomeApplication.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONParser jsonParser = new JSONParser();
        FileReader reader = null;
        try {
            reader = new FileReader("./configuration/dbConfiguration.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Object obj = null;
        try {
            obj = jsonParser.parse(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject jObject = (JSONObject) obj;
        databaseName.setPromptText((String) jObject.get("DATABASE"));
        portNumber.setPromptText(jObject.get("PORT").toString());
        userName.setPromptText((String) jObject.get("USER_ID"));
        password.setPromptText( jObject.get("PASSWORD").toString());
        serverName.setPromptText((String) jObject.get("SERVER"));



    }
    public void changeSettings() throws IOException {
        try {
            JSONObject dbSettings = new JSONObject();

            dbSettings.put("DRIVER_CLASS_NAME", "com.mysql.cj.jdbc.Driver");
            dbSettings.put("DATABASE", "mypersonalnetwork");
            dbSettings.put("PORT", 3306);
            dbSettings.put("PASSWORD", "root");
            dbSettings.put("SERVER", "localhost");
            dbSettings.put("USER_ID", "root");
            dbSettings.put("DBMS", "jdbc:mysql");

            if (!this.databaseName.getText().isEmpty() && !this.databaseName.getText().isBlank()) {
                dbSettings.put("DATABASE", this.databaseName.getText().trim());
            }

            if (!this.portNumber.getText().isEmpty() && !this.portNumber.getText().isBlank()) {
                dbSettings.put("PORT", Integer.valueOf(this.portNumber.getText().trim()));
            }

            if (!this.userName.getText().isEmpty() && !this.userName.getText().isBlank()) {
                dbSettings.put("USER_ID", this.userName.getText().trim());
            }

            if (!this.password.getText().isEmpty() && !this.password.getText().isBlank()) {
                dbSettings.put("PASSWORD", this.password.getText().trim());
            }

            if (!this.password.getText().isEmpty() && !this.password.getText().isBlank()) {
                dbSettings.put("SERVER", this.password.getText().trim());
            }

            FileWriter file = new FileWriter("./configuration/dbConfiguration.json");
            file.write(dbSettings.toJSONString());
            file.flush();

            LogMain.writeLog("Cambiate le impostazioni del database: " + dbSettings.toJSONString() + " .\n", Level.INFO, HomeApplication.class.getName());
            AllertDialogs.viewPopUp("INFORMATION", "Db Settings changed correctly!");
            initialize( getClass().getResource("add-people-view.fxml"),null);
            databaseName.clear();
            portNumber.clear();
            userName.clear();
            password.clear();
            serverName.clear();
        }catch (IOException e){
            LogMain.writeLog("Errore nel cambiare le impostazioni del database.\n", Level.WARNING, HomeApplication.class.getName());
            AllertDialogs.viewPopUp("ERROR", "Error changing database settings.");
        }
    }
}
