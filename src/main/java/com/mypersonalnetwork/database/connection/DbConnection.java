package com.mypersonalnetwork.database.connection;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mypersonalnetwork.logsystem.LogMain;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DbConnection {
    private static String DRIVER_CLASS_NAME;
    private String DBMS = "";
    private String SERVER = "";
    private String DATABASE = "";
    private int PORT = 3306;
    private String USER_ID = "";
    private String PASSWORD = "";
    private Connection conn;

    private final String pathFolder = "./exportDataDB";
    private final String nameFile = "/export-data-db.sql";

    public DbConnection() throws DatabaseConnectionException, FileNotFoundException, IOException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        initConnection();
    }

    private void initConnection() throws DatabaseConnectionException, FileNotFoundException,IOException,ParseException,ClassNotFoundException,InstantiationException,IllegalAccessException,SQLException{
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("./configuration/dbConfiguration.json");

        Object obj = jsonParser.parse(reader);
        JSONObject jObject = (JSONObject) obj;
        this.DRIVER_CLASS_NAME = (String) jObject.get("DRIVER_CLASS_NAME");
        this.DBMS = (String) jObject.get("DBMS");
        this.SERVER = (String) jObject.get("SERVER");
        this.DATABASE = (String) jObject.get("DATABASE");
        this.PORT =  Integer.valueOf(jObject.get("PORT").toString());
        this.USER_ID = (String) jObject.get("USER_ID");
        this.PASSWORD = (String) jObject.get("PASSWORD");

        Class.forName(DRIVER_CLASS_NAME).newInstance();

        String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
        this.conn = DriverManager.getConnection(connectionString);


    }
    private void updateDbConfiguration(String server, String database, int port, String user, String password) throws IOException {
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

    public void closeConnection() throws SQLException {
        this.conn.close();
    }

    public void exportData() throws IOException, DatabaseConnectionException, SQLException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        initConnection();
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
                String personTableData = "INSERT INTO person(idPerson,name,surname,adress,city,province,phoneNumber,workPosition,workCity,workCompany,geoLocation,birthday,cityBorn) VALUES ";
                String queryGetPerson = "SELECT * FROM person;";
                ResultSet rs = this.conn.createStatement().executeQuery(queryGetPerson);

                while (rs.next()){
                    personTableData += "(";
                    personTableData += String.valueOf(rs.getInt("idPerson")) + ",";
                    personTableData += rs.getString("name") + ",";
                    personTableData += rs.getString("surname") + ",";
                    personTableData += rs.getString("adress") + ",";
                    personTableData += rs.getString("city") + ",";
                    personTableData += rs.getString("province") + ",";
                    personTableData += rs.getString("phoneNumber") + ",";
                    personTableData += rs.getString("workPosition") + ",";
                    personTableData += rs.getString("workCity") + ",";
                    personTableData += rs.getString("workCompany") + ",";
                    personTableData += rs.getString("geoLocation") + ",";
                    personTableData += rs.getString("birthday") + ",";
                    personTableData += rs.getString("cityBorn");
                    personTableData += "),";
                }
                personTableData = personTableData.substring(0,personTableData.length()-1)+";";

                //GET TYPERELATION TABLE DATA
                String typeRelationData = "INSERT INTO typeRelation(idTypeRelation,nameRelation) VALUES ";
                String queryGetRelationData = "SELECT * FROM typeRelation;";
                rs = this.conn.createStatement().executeQuery(queryGetRelationData);

                while (rs.next()){
                    typeRelationData += "(";
                    typeRelationData += String.valueOf(rs.getInt("idTypeRelation")) + ",";
                    typeRelationData += rs.getString("nameRelation") + ",";
                    typeRelationData += "),";
                }
                typeRelationData = typeRelationData.substring(0,typeRelationData.length()-1)+";";

                //GET RELATIONSHIPDATA TABLE DATA
                String relationshipData = "INSERT INTO relationship(idRelationship,idPerson1,idPerson2,idTypeRelation) VALUES ";
                String queryGetRelationshipData = "SELECT * FROM relationship;";
                rs = this.conn.createStatement().executeQuery(queryGetRelationshipData);

                while (rs.next()){
                    relationshipData += "(";
                    relationshipData += String.valueOf(rs.getInt("idRelationship")) + ",";
                    relationshipData += rs.getString("idPerson1") + ",";
                    relationshipData += rs.getString("idPerson2") + ",";
                    relationshipData += rs.getString("idTypeRelation") + ",";
                    relationshipData += "),";
                }
                relationshipData = relationshipData.substring(0,relationshipData.length()-1)+";";

                //GET PLACE TABLE DATA
                String placeData = "INSERT INTO place(idPlace,name,geoLocation,adress,city,cap,typePlace) VALUES ";
                String queryGetplaceData = "SELECT * FROM place;";
                rs = this.conn.createStatement().executeQuery(queryGetplaceData);

                while (rs.next()){
                    placeData += "(";
                    placeData += String.valueOf(rs.getInt("idPlace")) + ",";
                    placeData += rs.getString("name") + ",";
                    placeData += rs.getString("geoLocation") + ",";
                    placeData += rs.getString("adress") + ",";
                    placeData += rs.getString("city") + ",";
                    placeData += rs.getString("cap") + ",";
                    placeData += rs.getString("typePlace") + ",";
                    placeData += "),";
                }
                placeData = placeData.substring(0,placeData.length()-1)+";";

                String finalQuery = initDB + "\n"+defaultData+"\n"+personTableData+"\n"+
                        typeRelationData+"\n"+relationshipData+"\n"+placeData;

                Files.write(Path.of(pathFolder+nameFile),finalQuery.getBytes());


            }
        } catch (IOException e) {
            LogMain.writeLog("Impossibile scrivere il file relativo all'export dei dati", Level.WARNING,DbConnection.class.getName());
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
