package ui;

import ui.interfaces.MoneyDialog;
import ui.interfaces.CurrencyDialog;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Money;
import model.Number;

public class MoneyDialogPanel extends JPanel implements MoneyDialog {

    private Double amount;
    private CurrencyDialog currencyDialog;

    public MoneyDialogPanel() {
        super(new FlowLayout(FlowLayout.LEFT));
        this.amount = 0.0;
        this.createComponents();
    }

    private JTextField createAmountField() {
        final JTextField textField = new JTextField(10);
        textField.setText("0.0");
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                amount = Double.parseDouble(textField.getText());
                ;
            }
        });
        return textField;
    }

    private JPanel createCurrencyDialog() {
        CurrencyDialogPanel panel = new CurrencyDialogPanel();
        currencyDialog = panel;
        return panel;
    }

    private void createComponents() {
        this.add(createAmountField());
        this.add(createCurrencyDialog());
    }

    public void createJLabel(String result) {
        this.add(new JLabel(result));
    }

    @Override
    public Money getMoney() {
        currencyDialog = (CurrencyDialogPanel) currencyDialog;
        return new Money(new Number(amount), currencyDialog.getCurrency());
    }
}
