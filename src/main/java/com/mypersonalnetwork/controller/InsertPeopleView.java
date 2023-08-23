package com.mypersonalnetwork.controller;


import com.mypersonalnetwork.HomeApplication;
import com.mypersonalnetwork.allert.AllertDialogs;
import com.mypersonalnetwork.database.connection.DatabaseConnectionException;
import com.mypersonalnetwork.database.connection.DbConnection;
import com.mypersonalnetwork.logsystem.LogMain;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

public class InsertPeopleView {
    public TextField name;
    public TextField surname;
    public TextField address;
    public TextField city;
    public TextField province;
    public TextField phoneNumber;
    public DatePicker birthday;
    public TextField cityBorn;
    public TextField workPosition;
    public TextField workCity;
    public TextField workCompany;
    public TextField workPhone;

    public void saveInformation() throws IOException {
        DbConnection dbConnection = null;
        try {
            dbConnection = new DbConnection();
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        checkEmptyData();

        if(this.workPosition.getText().isBlank()||this.workPosition.getText().isEmpty()){
            if(AllertDialogs.viewPopUp("CONFIRMATION","You have a blank field for Work Position. Is "+this.name.getText().trim()+" uneployad?").equals("ConfirmationNO")){
                AllertDialogs.viewPopUp("INFORMATION","Set the work info");
            }
        }else{
            checkData();
            loadData(dbConnection);
            clearFields();
        }

    }

    private void clearFields() {
        this.name.clear();
        this.surname.clear();
        this.address.clear();
        this.city.clear();
        this.province.clear();
        this.phoneNumber.clear();
        this.birthday.getEditor().clear();
        this.cityBorn.clear();
        this.workPosition.clear();
        this.workCity.clear();
        this.workCompany.clear();
        this.workPhone.clear();
    }

    private void checkEmptyData() {
        boolean nameEmpty = this.name.getText().isBlank() || this.name.getText().isEmpty();
        boolean surnameEmpty = this.surname.getText().isBlank() || this.surname.getText().isEmpty();
        boolean addressEmpty = this.address.getText().isBlank() || this.address.getText().isEmpty();
        boolean cityEmpty = this.city.getText().isBlank() || this.city.getText().isEmpty();
        boolean provinceEmpty = this.province.getText().isBlank() || this.province.getText().isEmpty();
        boolean phoneNumberEmpty = this.phoneNumber.getText().isBlank() || this.phoneNumber.getText().isEmpty();
        boolean birthdayEmpty = this.birthday.getValue()==null;
        boolean cityBornEmpty = this.cityBorn.getText().isBlank() || this.city.getText().isBlank();


        if(nameEmpty && surnameEmpty && addressEmpty && cityEmpty && provinceEmpty && phoneNumberEmpty && birthdayEmpty && cityBornEmpty){
            AllertDialogs.viewPopUp("WARNING","Set info about the person");
        }
    }

    private void checkData() {
        try{
            Integer.parseInt(this.phoneNumber.getText());
            Integer.parseInt(this.workPhone.getText());
        }catch (NumberFormatException e){
            AllertDialogs.viewPopUp("WARNING","Wrong phone number.");
            throw new RuntimeException();
        }
    }

    private void loadData(DbConnection dbConnection) throws IOException {
        String sqlQuery=null;
        try{
            sqlQuery = "INSERT INTO person(idPerson,name,surname,adress,city,province,phoneNumber,workNumber,workPosition,workCity,workCompany,birthday,cityBorn) VALUES("+
                               "1,'"+this.name.getText()+"','"+this.surname.getText()+"','"+this.address.getText()+"','"+this.city.getText()+"','"+this.province.getText()+"','"+this.phoneNumber.getText()+"','"+this.workPhone.getText()+"','"
                                +this.workPosition.getText()+"','"+this.workCity.getText()+"','"+this.workCompany.getText()+"',to_date(cast("+this.birthday.getValue().toString().trim()+"as TEXT),'YYYY-MM-DD'),'"+this.cityBorn.getText()+"');";
            if(!dbConnection.updateQuery(sqlQuery)){
                AllertDialogs.viewPopUp("INFORMATION","Person added.");
            }else{
                AllertDialogs.viewPopUp("ERROR","I can't add the person.");
            }
        }catch(Exception e){
            AllertDialogs.viewPopUp("ERROR","Impossible to add person.");
            LogMain.writeLog(e.getClass()+" Impossibile aggiunngere la persona. Query: "+sqlQuery+"\n", Level.WARNING,HomeApplication.class.getName());
        }
    }
}
