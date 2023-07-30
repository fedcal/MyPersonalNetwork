package com.mypersonalnetwork.database.connection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
