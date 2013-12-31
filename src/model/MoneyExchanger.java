package model;

public class MoneyExchanger {

    public Money exchange(Money money, ExchangeRate exchangeRate) {
        return new Money(money.getAmount() * exchangeRate.getRate(), exchangeRate.getToCurrency());
    }
}
