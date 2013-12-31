package persistance;

import java.util.Date;
import model.Currency;
import model.ExchangeRate;

public interface ExchangeRateLoader {

    public ExchangeRate load(Currency from, Currency to, Date date);

    public ExchangeRate load(Currency from, Currency to);
}
