package nl.paulinternet.gtasaveedit.view.connected;

import nl.paulinternet.libsavegame.event.Event;
import nl.paulinternet.libsavegame.event.EventHandler;
import nl.paulinternet.libsavegame.variables.VariableIntegerImpl;
import nl.paulinternet.gtasaveedit.view.swing.PComboBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> the type of the elements of this combo box
 */
public class ConnectedComboBox<T> extends PComboBox<T> {
    private class Handler implements ItemListener, EventHandler {
        private boolean disabled;

        @Override
        public void handleEvent(Event e) {
            if (!disabled) {
                int index = -1;
                int value = var.getIntValue();
                for (int i = 0; i < values.size(); i++) {
                    if (value == values.get(i)) {
                        index = i;
                        break;
                    }
                }
                disabled = true;
                setSelectedIndex(index);
                disabled = false;
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (!disabled) {
                int index = getSelectedIndex();
                if (index >= 0 && index < values.size()) {
                    disabled = true;
                    var.setIntValue(values.get(index));
                    disabled = false;
                }
            }
        }
    }

    protected VariableIntegerImpl var;
    private List<Integer> values;

    public ConnectedComboBox(VariableIntegerImpl var) {
        this.var = var;
        values = new ArrayList<Integer>();

        // Observe
        Handler h = new Handler();
        addItemListener(h);
        var.onChange().addHandler(h);
    }

    public void addItem(int value, T name) {
        super.addItem(name);
        values.add(value);
    }
}
