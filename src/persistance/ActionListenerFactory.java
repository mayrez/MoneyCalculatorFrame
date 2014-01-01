package persistance;

import java.awt.event.ActionListener;

public interface ActionListenerFactory {

    public ActionListener getAction(String action);
}
