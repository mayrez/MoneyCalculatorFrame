package persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Currency;
import model.Number;
import model.ExchangeRate;

public class DataBaseExchangeRateLoader implements ExchangeRateLoader {

    private Connection connection;

    public DataBaseExchangeRateLoader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        if (from.getCode().equalsIgnoreCase(to.getCode())) {
            return new ExchangeRate(from, to, new Number(1));
        }

        if (from.getCode().equalsIgnoreCase("EUR")) {
            return new ExchangeRate(from, to, getExchangeRate_EUR_to(to));
        } else {
            return new ExchangeRate(from, to,
                    Number.multiplicate(getExchangeRate_from_EUR(from), getExchangeRate_EUR_to(to)));
        }
    }

    @Override
    public ExchangeRate load(Currency from, Currency to, Date date) {
        if (from.getCode().equalsIgnoreCase(to.getCode())) {
            return new ExchangeRate(date, from, to, new Number(1));
        }

        if (from.getCode().equalsIgnoreCase("EUR")) {
            return new ExchangeRate(date, from, to, getExchangeRate_EUR_to(date, to));
        } else {
            return new ExchangeRate(date, from, to,
                    Number.multiplicate(getExchangeRate_from_EUR(date, from), getExchangeRate_EUR_to(date, to)));
        }
    }

    private Number getExchangeRate_from_EUR(Currency from) {
        return new Number(1).divide(getExchangeRate_EUR_to(from));
    }

    private Number getExchangeRate_from_EUR(Date date, Currency from) {
        return new Number(1).divide(getExchangeRate_EUR_to(date, from));
    }

    private Number getExchangeRate_EUR_to(Currency currency) {
        if (currency.getCode().equalsIgnoreCase("EUR")) {
            return new Number(1);
        }
        String query = "SELECT * FROM CAMBIO_EUR_A WHERE DIVISA = '" + currency.getCode() + "'";
        return getRateFromDataBase(query);
    }

    private Number getExchangeRate_EUR_to(Date date, Currency currency) {
        if (currency.getCode().equalsIgnoreCase("EUR")) {
            return new Number(1);
        }
        String query = "SELECT * FROM CAMBIO_EUR_A WHERE DIVISA = '" + currency.getCode() + "and" + "WHERE alta=" + date + "'";
        return getRateFromDataBase(query);
    }

    private Number getRateFromDataBase(String query) {
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            if (!result.next()) {
                throw new SQLException();
            }

            return new Number(result.getBigDecimal("CAMBIO").doubleValue());
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseExchangeRateLoader.class.getName()).log(Level.SEVERE, null, ex);
            return new Number(0);
        }
    }
}
