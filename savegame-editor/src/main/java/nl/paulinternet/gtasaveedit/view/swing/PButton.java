package nl.paulinternet.gtasaveedit.view.swing;

import nl.paulinternet.libsavegame.event.Event;
import nl.paulinternet.libsavegame.event.ReportableEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PButton extends JButton implements ActionListener {
    private ReportableEvent onClick;

    public PButton() {
    }

    public PButton(String title) {
        super(title);
    }

    public Event onClick() {
        if (onClick == null) {
            onClick = new ReportableEvent();
            addActionListener(this);
        }
        return onClick;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        onClick.report();
    }
}
