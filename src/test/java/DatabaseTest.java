import com.mypersonalnetwork.database.connection.DatabaseConnectionException;
import com.mypersonalnetwork.database.connection.DbConnection;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class DatabaseTest {

    @Test
    public void openConnection() throws DatabaseConnectionException, SQLException, IOException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DbConnection dbConnection = new DbConnection();
        Assertions.assertNotNull(dbConnection);
        Assertions.assertEquals(3306, dbConnection.getPORT());
        Assertions.assertEquals("jdbc:mysql",dbConnection.getDBMS());
        Assertions.assertEquals("mypersonalnetwork", dbConnection.getDATABASE());
        Assertions.assertEquals("root",dbConnection.getPASSWORD());
        Assertions.assertEquals("localhost",dbConnection.getSERVER());
    }

    @Test
    public void closeConnection() throws DatabaseConnectionException, SQLException, IOException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DbConnection dbConnection = new DbConnection();
        Assertions.assertNotNull(dbConnection);
        dbConnection.closeConnection();
        Assertions.assertTrue(dbConnection.getConnection().isClosed());
    }

    @Test
    public void backupFile() throws DatabaseConnectionException, SQLException, IOException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DbConnection dbConnection = new DbConnection();
        Boolean createTable = dbConnection.updateQuey("CREATE TABLE IF NOT EXISTS typeRelation(idTypeRelation int PRIMARY KEY,nameRelation VARCHAR(50));");
        createTable = dbConnection.updateQuey("CREATE TABLE IF NOT EXISTS person(idPerson int PRIMARY KEY, name VARCHAR(50),surname VARCHAR(50),adress VARCHAR(150),\n" +
                "                                  city VARCHAR(50), province VARCHAR(50), phoneNumber VARCHAR(20),workPosition VARCHAR(50),\n" +
                "                                  workCity VARCHAR(50),workCompany VARCHAR(200),geoLocation VARCHAR(200),birthday DATE,\n" +
                "                                  cityBorn VARCHAR(180));");
        createTable = dbConnection.updateQuey("CREATE TABLE IF NOT EXISTS relationship(idRelationship int PRIMARY KEY, idPerson1 int, idPerson2 int, idTypeRelation int,\n" +
                "                                        FOREIGN KEY (idPerson1) REFERENCES person(idPerson),FOREIGN KEY (idPerson2) REFERENCES person(idPerson),\n" +
                "                                        FOREIGN KEY (idTypeRelation) REFERENCES typeRelation(idTypeRelation));");
        createTable = dbConnection.updateQuey("CREATE TABLE IF NOT EXISTS place (idPlace int PRIMARY KEY, name VARCHAR(250), geoLocation VARCHAR(250), adress VARCHAR(250),\n" +
                "                                  city VARCHAR(50),cap VARCHAR(15), typePlace VARCHAR(50));");

        //Boolean queryResult = dbConnection.updateQuey("INSERT INTO typeRelation(idTypeRelation,nameRelation) VALUE (58,\'CIAO\');");
        //Assertions.assertTrue(queryResult);
        dbConnection.exportData();
        Assertions.assertTrue(Files.exists(Path.of(dbConnection.getPathFolder()+dbConnection.getNameFile())));
    }

}
