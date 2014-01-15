package model;

public class MoneyExchanger {

    public Money exchange(Money money, ExchangeRate exchangeRate) {
        return new Money(Number.multiplicate(money.getAmount(),exchangeRate.getRate()), exchangeRate.getToCurrency());
    }
}
