package ui;

import ui.interfaces.CurrencyDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import model.Currency;
import model.CurrencySet;

public class CurrencyDialogPanel extends JPanel implements CurrencyDialog {

    private Currency currency;

    public CurrencyDialogPanel() {
        super(new FlowLayout(FlowLayout.LEFT));
        this.add(createComboBox());
        this.currency = new Currency("EUR");
    }

    private JComboBox createComboBox() {       

        final JComboBox comboBox = new JComboBox();
        for (Currency currencies : CurrencySet.getInstance()) {
            comboBox.addItem(currencies);
            currency = CurrencySet.getInstance().iterator().next();
            comboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currency = (Currency) comboBox.getSelectedItem();
                }
            });
        }

        return comboBox;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }
}
