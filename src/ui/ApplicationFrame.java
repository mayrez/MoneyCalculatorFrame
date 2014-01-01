package ui;

import persistance.ActionListenerFactory;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

public class ApplicationFrame extends JFrame {

    private MoneyDialog moneyDialog;
    private CurrencyDialog currencyDialog;
    private ActionListenerFactory factory;

    public ApplicationFrame(ActionListenerFactory actionListenerFactory) {
        super("Money calculator");
        this.factory = actionListenerFactory;
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.createComponents();
        this.setVisible(true);
    }

    private void createComponents() {
        this.add(createMoneyExchangePanel());
        this.add(createToolbar(), BorderLayout.SOUTH);
    }

    private JPanel createMoneyExchangePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(createMoneyDialogPanel());
        panel.add(createCurrencyDialogPanel());
        return panel;
    }

    private JPanel createMoneyDialogPanel() {
        MoneyDialogPanel panel = new MoneyDialogPanel();
        moneyDialog = panel;
        return panel;
    }

    private JPanel createCurrencyDialogPanel() {
        CurrencyDialogPanel panel = new CurrencyDialogPanel();
        currencyDialog = panel;
        return panel;
    }

    private JPanel createToolbar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(createCalculateButton());
        panel.add(createExitButton());
        return panel;
    }

    private JButton createCalculateButton() {
        JButton button = new JButton("Calculate");
        button.addActionListener(factory.getAction("calculate"));
        return button;
    }

    private JButton createExitButton() {
        JButton button = new JButton("Exit");
        button.addActionListener(factory.getAction("exit"));
        return button;
    }

    public MoneyDialog getMoneyDialog() {
        return moneyDialog;
    }

    public CurrencyDialog getCurrencyDialog() {
        return currencyDialog;
    }
}
