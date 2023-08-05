package com.mypersonalnetwork.database.connection;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mypersonalnetwork.HomeApplication;
import com.mypersonalnetwork.allert.AllertDialogs;
import com.mypersonalnetwork.logsystem.LogMain;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DbConnection {
    private static String DRIVER_CLASS_NAME;
    private static String DBMS = "";
    private static String SERVER = "";
    private static String DATABASE = "";
    private static int PORT = 3306;
    private static String USER_ID = "";
    private static String PASSWORD = "";
    private static Connection conn;

    private static final String pathFolder = "./exportDataDB";
    private static final String nameFile = "/export-data-db.sql";

    public DbConnection() throws DatabaseConnectionException, FileNotFoundException, IOException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        initConnection();
    }

    private static void initConnection() throws DatabaseConnectionException, FileNotFoundException,IOException,ParseException,ClassNotFoundException,InstantiationException,IllegalAccessException,SQLException{
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("./configuration/dbConfiguration.json");

        Object obj = jsonParser.parse(reader);
        JSONObject jObject = (JSONObject) obj;
        DRIVER_CLASS_NAME = (String) jObject.get("DRIVER_CLASS_NAME");
        DBMS = (String) jObject.get("DBMS");
        SERVER = (String) jObject.get("SERVER");
        DATABASE = (String) jObject.get("DATABASE");
        PORT =  Integer.valueOf(jObject.get("PORT").toString());
        USER_ID = (String) jObject.get("USER_ID");
        PASSWORD = (String) jObject.get("PASSWORD");

        Class.forName(DRIVER_CLASS_NAME).newInstance();

        String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
        conn = DriverManager.getConnection(connectionString);


    }
    private static void updateDbConfiguration(String server, String database, int port, String user, String password) throws IOException {
        JSONObject dbData = new JSONObject();
        dbData.put("DRIVER_CLASS_NAME","com.mysql.cj.jdbc.Driver");
        dbData.put("DBMS","jdbc:mysql");
        dbData.put("SERVER",server);
        dbData.put("DATABASE",database);
        dbData.put("PORT",port);
        dbData.put("USER_ID",user);
        dbData.put("PASSWORD",password);

        FileWriter file = new FileWriter("./configuration/dbConfiguration.json");
        file.write(dbData.toJSONString());
        file.flush();

    }

    public Connection getConnection() {
        return this.conn;
    }

    public static void closeConnection() throws SQLException {
        conn.close();
    }

    public static void exportData() throws IOException, DatabaseConnectionException, SQLException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        if(!Files.exists(Path.of(pathFolder))) {
            try {
                Files.createDirectories(Path.of(pathFolder));
            } catch (IOException e) {
                LogMain.writeLog("Impossibile creare la cartella per l'export dei dati", Level.WARNING,DbConnection.class.getName());
            }
        }else if(Files.exists(Path.of(pathFolder+nameFile))){
            try {
                Files.delete(Path.of(pathFolder+nameFile));
            } catch (IOException e) {
                LogMain.writeLog("Impossibile eliminare il file relativo all'export dei dati", Level.WARNING,DbConnection.class.getName());
            }
        }
        File exportDataFile = new File(pathFolder+nameFile);
        try {
            if(exportDataFile.createNewFile()){
                // GET INITDB FILE
                Stream<String> lines = Files.lines(Path.of("src/main/resources/db/initDb.sql"));
                String initDB = lines.collect(Collectors.joining("\n"));
                lines.close();

                // GET DEFAULT-DATA FILE
                lines = Files.lines(Path.of("src/main/resources/db/default-data.sql"));
                String defaultData = lines.collect(Collectors.joining("\n"));
                lines.close();

                // GET PERSON TABLE DATA
                String personTableData = "";
                ResultSet rs;

                try {
                    rs = getQuery("SELECT * FROM person;");
                    personTableData = "INSERT INTO person(idPerson,name,surname,adress,city,province,phoneNumber,workPosition,workCity,workCompany,geoLocation,birthday,cityBorn) VALUES ";
                    while (rs.next()){
                        personTableData += "(";
                        personTableData += String.valueOf(rs.getInt("idPerson")) + ",";
                        personTableData += "\'" + rs.getString("name") + "\'" + ",";
                        personTableData += "\'" + rs.getString("surname")+ "\'" + ",";
                        personTableData += "\'" + rs.getString("adress")+ "\'" + ",";
                        personTableData += "\'" + rs.getString("city")+ "\'" + ",";
                        personTableData += "\'" + rs.getString("province")+ "\'" + ",";
                        personTableData += "\'" + rs.getString("phoneNumber")+ "\'" + ",";
                        personTableData += "\'" + rs.getString("workPosition")+ "\'" + ",";
                        personTableData += "\'" + rs.getString("workCity")+ "\'" + ",";
                        personTableData += "\'" + rs.getString("workCompany")+ "\'" + ",";
                        personTableData += "\'" + rs.getString("geoLocation")+ "\'" + ",";
                        personTableData += "DATE(" + rs.getDate("birthday") +")" + ",";
                        personTableData += "\'" + rs.getString("cityBorn")+ "\'";
                        personTableData += "),";
                    }
                    personTableData = personTableData.substring(0,personTableData.length()-1)+";";
                }catch (Exception e){

                }

                //GET TYPERELATION TABLE DATA
                String typeRelationData = "";

                try {
                    typeRelationData = "INSERT INTO typeRelation(idTypeRelation,nameRelation) VALUES ";
                    rs = getQuery("SELECT * FROM typeRelation;");
                    while (rs.next()){
                        typeRelationData += "(";
                        typeRelationData += String.valueOf(rs.getInt("idTypeRelation")) + ",";
                        typeRelationData += "\'" + rs.getString("nameRelation") + "\'" +  ",";
                        typeRelationData += "),";
                    }
                    typeRelationData = typeRelationData.substring(0,typeRelationData.length()-1)+";";
                }catch (Exception e){

                }

                //GET RELATIONSHIPDATA TABLE DATA
                String relationshipData = "";

                try {
                    relationshipData = "INSERT INTO relationship(idRelationship,idPerson1,idPerson2,idTypeRelation) VALUES ";
                    rs = getQuery("SELECT * FROM relationship;");
                    while (rs.next()){
                        relationshipData += "(";
                        relationshipData += String.valueOf(rs.getInt("idRelationship")) + ",";
                        relationshipData += rs.getString("idPerson1") + ",";
                        relationshipData += rs.getString("idPerson2") + ",";
                        relationshipData += rs.getString("idTypeRelation") + ",";
                        relationshipData += "),";
                    }
                    relationshipData = relationshipData.substring(0,relationshipData.length()-1)+";";
                }catch (Exception e){

                }

                //GET PLACE TABLE DATA
                String placeData = "";

                try {
                    placeData = "INSERT INTO place(idPlace,name,geoLocation,adress,city,cap,typePlace) VALUES ";
                    typeRelationData = "INSERT INTO typeRelation(idTypeRelation,nameRelation) VALUES ";
                    rs = conn.createStatement().executeQuery("SELECT * FROM place;");

                    while (rs.next()){
                        placeData += "(";
                        placeData += String.valueOf(rs.getInt("idPlace")) + ",";
                        placeData += "\'" + rs.getString("name") + "\'" + ",";
                        placeData += "\'" + rs.getString("geoLocation") + "\'"+ ",";
                        placeData += "\'" + rs.getString("adress") + "\'"+ ",";
                        placeData += "\'" + rs.getString("city") + "\'"+ ",";
                        placeData += "\'" + rs.getString("cap") + "\'"+ ",";
                        placeData += "\'" + rs.getString("typePlace") + "\'"+ ",";
                        placeData += "),";
                    }
                    placeData = placeData.substring(0,placeData.length()-1)+";";
                }catch (Exception e){

                }

                String finalQuery = initDB + "\n"+defaultData+"\n"+personTableData+"\n"+
                        typeRelationData+"\n"+relationshipData+"\n"+placeData;

                Files.write(Path.of(pathFolder+nameFile),finalQuery.getBytes());


            }

        } catch (IOException e) {
            LogMain.writeLog("Impossibile scrivere il file relativo all'export dei dati", Level.WARNING,DbConnection.class.getName());
        }
    }

    public static ResultSet getQuery(String query) throws SQLException {
        return conn.createStatement().executeQuery(query);
    }
    public static Boolean updateQuey(String query){
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static String getDriverClassName() {
        return DRIVER_CLASS_NAME;
    }

    public String getDBMS() {
        return DBMS;
    }

    public String getSERVER() {
        return SERVER;
    }

    public String getDATABASE() {
        return DATABASE;
    }

    public int getPORT() {
        return PORT;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public Connection getConn() {
        return conn;
    }

    public String getPathFolder() {
        return pathFolder;
    }

    public String getNameFile() {
        return nameFile;
    }
}
