package Application;

import ui.ApplicationFrame;
import persistance.ActionListenerFactory;
import control.CalculateCommand;
import control.Command;
import control.CommandDictionary;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {

    public static void main(String[] args) {
        new Application().execute();
    }

    private  void execute() {
        final CommandDictionary commandDictionary = new CommandDictionary();
        ActionListenerFactory factory = createActionListenerFactory(commandDictionary);
        final ApplicationFrame frame = new ApplicationFrame(factory);
        setCommandDictionary(commandDictionary, frame);
        
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

    private void setCommandDictionary(CommandDictionary commandDictionary, final ApplicationFrame frame) {
        commandDictionary.register("calculate", new CalculateCommand(frame.getMoneyDialog(), frame.getCurrencyDialog()));
        commandDictionary.register("exit", new Command() {
            @Override
            public void execute() {
                frame.dispose();
            }
        });
    }
}
