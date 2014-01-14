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
    private static DataBaseCurrencySetLoader INSTANCE;

    private DataBaseCurrencySetLoader(Connection connection) {
        this.connection = connection;
    }

    public static CurrencySetLoader getInstance(Connection connection) {
        if (INSTANCE == null) {
            INSTANCE = new DataBaseCurrencySetLoader(connection);
        }

        return INSTANCE;

    }


    @Override
    public CurrencySet load() {
        CurrencySet currencySet = CurrencySet.getInstance();
        try {

            createResultSet();
            currencySet.add(new Currency("EUR"));
            while (resultSet.next()) {
                currencySet.add(new Currency(resultSet.getString("DIVISA")));
            }


        } catch (SQLException ex) {
            Logger.getLogger(DataBaseCurrencySetLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currencySet;
    }

    private void createResultSet() {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CAMBIO_EUR_A");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseCurrencySetLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
