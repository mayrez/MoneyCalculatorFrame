package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Currency;
import model.CurrencySet;

public class DataBaseCurrencySetLoader implements CurrencySetLoader {

    private ResultSet resultSet;
    private Connection connection;
    private Statement statement;
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USR = "system";
    private static final String PASSWD = "orcl";

    public DataBaseCurrencySetLoader() {
    }

    @Override
    public CurrencySet load() {
        CurrencySet currencySet = CurrencySet.getInstance();
        try {

            createConection();
            createStatement();

            while (resultSet.next()) {
                currencySet.add(new Currency("DIVISA"));
            }


        } catch (SQLException ex) {
            Logger.getLogger(DataBaseCurrencySetLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currencySet;
    }

    private void createConection() throws SQLException {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        connection = DriverManager.getConnection(URL, USR, PASSWD);

    }

    private void createStatement() {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CAMBIO_EUR_A");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseCurrencySetLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
