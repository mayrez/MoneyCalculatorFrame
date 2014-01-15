package Application;

import ui.ApplicationFrame;
import ui.interfaces.ActionListenerFactory;
import control.CalculateCommand;
import control.Command;
import control.CommandDictionary;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistance.CurrencySetLoader;
import persistance.DataBaseCurrencySetLoader;
import persistance.DataBaseExchangeRateLoader;
import persistance.ExchangeRateLoader;

public class Application {

    public static void main(String[] args) {
        new Application().execute();
    }

    private void execute() {
        try {
            CurrencySetLoader currencySetLoader = createCurrencySetLoader();
            currencySetLoader.load();
        } catch (Exception ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }

        final CommandDictionary commandDictionary = new CommandDictionary();
        ActionListenerFactory factory = createActionListenerFactory(commandDictionary);
        final ApplicationFrame frame = new ApplicationFrame(factory);
        frame.execute();
        try {

            ExchangeRateLoader exchangeRateLoader = createExchangeRateLoader();
            setCommandDictionary(commandDictionary, frame, exchangeRateLoader);
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private ActionListenerFactory createActionListenerFactory(final CommandDictionary commandDictionary) {
        return new ActionListenerFactory() {
            @Override
            public ActionListener getAction(final String action) {
                return new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        commandDictionary.get(action).execute();
                    }
                };
            }
        };
    }

    private void setCommandDictionary(CommandDictionary commandDictionary, final ApplicationFrame frame, ExchangeRateLoader exchangeRateLoader) {
        commandDictionary.register("calculate", new CalculateCommand(frame.getMoneyDialog(), frame.getCurrencyDialog(), exchangeRateLoader));
        commandDictionary.register("exit", new Command() {
            @Override
            public void execute() {
                frame.dispose();
            }
        });
    }

    private ExchangeRateLoader createExchangeRateLoader() throws SQLException {
        return createDataBaseExchangeRateLoader();
    }

    private ExchangeRateLoader createDataBaseExchangeRateLoader() throws SQLException {
        return new DataBaseExchangeRateLoader(createConnection());
    }

    private Connection createConnection() {
        try {
            Connection connection;
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "system");

            return connection;
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private CurrencySetLoader createCurrencySetLoader() {
        return createDataBaseCurrencySetLoader();

    }

    private CurrencySetLoader createDataBaseCurrencySetLoader() {
        return DataBaseCurrencySetLoader.getInstance(createConnection());

    }
}
