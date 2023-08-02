import com.mypersonalnetwork.database.connection.DatabaseConnectionException;
import com.mypersonalnetwork.database.connection.DbConnection;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
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
    public void backupFile(){

    }

}
