package nl.paulinternet.gtasaveedit.view.menu;

import nl.paulinternet.gtasaveedit.view.swing.PMenuItem;
import nl.paulinternet.gtasaveedit.view.window.MainWindow;
import nl.paulinternet.libsavegame.Savegame;
import nl.paulinternet.libsavegame.SavegameModel;
import nl.paulinternet.libsavegame.exceptions.ErrorMessageException;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static nl.paulinternet.libsavegame.Util.MAC;

public class QuickLoad extends PMenuItem {
    private int number;

    public QuickLoad(int number) {
        this.number = number;

        // Accelerator
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0 + number, MAC ? InputEvent.META_MASK : InputEvent.CTRL_MASK));

        // Observe
        SavegameModel.quickLoadUpdate.addHandler(this, "updateText");
        onClick().addHandler(this, "loadSavegame");

        // Set text
        updateText();
    }

    @SuppressWarnings("unused") //used in event
    public void loadSavegame() {
        try {
            Savegame.load(SavegameModel.getSavegameFile(number));
        } catch (ErrorMessageException e) {
            JOptionPane.showMessageDialog(MainWindow.getInstance(), e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings({"WeakerAccess", "Duplicates"}) //used in handler
    public void updateText() {
        String title = SavegameModel.quickLoad.get(number).getValue();
        if (title == null) {
            setEnabled(false);
            setText(number + ".");
        } else {
            setEnabled(true);
            setText(number + ". " + title);
        }
    }
}
