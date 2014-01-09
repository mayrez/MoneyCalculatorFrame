package control;

import model.ExchangeRate;
import model.Money;
import model.MoneyExchanger;
import persistance.ExchangeRateLoader;
import ui.interfaces.CurrencyDialog;
import ui.interfaces.MoneyDialog;
import ui.MoneyDialogPanel;

public class CalculateCommand extends Command {

    private final MoneyDialog moneyDialog;
    private final CurrencyDialog currencyDialog;
    private final ExchangeRateLoader exchangeRateLoader;

    public CalculateCommand(MoneyDialog moneyDialog, CurrencyDialog currencyDialog, ExchangeRateLoader exchangeRateLoader) {
        this.moneyDialog = moneyDialog;
        this.currencyDialog = currencyDialog;
        this.exchangeRateLoader = exchangeRateLoader;
    }

    @Override
    public void execute() {
        ExchangeRate exchangeRate = exchangeRateLoader.load(moneyDialog.getMoney().getCurrency(), currencyDialog.getCurrency());
        Money result = exchange(moneyDialog.getMoney(), exchangeRate);
        MoneyDialogPanel moneyDialogPanel = (MoneyDialogPanel) moneyDialog;
        moneyDialogPanel.createJLabel(result + " " + currencyDialog.getCurrency());
        System.out.println(result + " " + currencyDialog.getCurrency());
    }

    private Money exchange(Money money, ExchangeRate exchangeRate) {
        MoneyExchanger exchanger = new MoneyExchanger();
        return exchanger.exchange(money, exchangeRate);
    }
}
