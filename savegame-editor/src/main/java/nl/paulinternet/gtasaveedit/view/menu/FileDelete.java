package nl.paulinternet.gtasaveedit.view.menu;

import nl.paulinternet.libsavegame.SavegameModel;
import nl.paulinternet.libsavegame.Settings;
import nl.paulinternet.libsavegame.io.FileSystem;
import nl.paulinternet.gtasaveedit.view.window.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

class FileDelete extends JMenuItem implements ActionListener {
    public FileDelete() {
        super("Delete...");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // Create filechooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(FileSystem.getSavegameDirectory());
        fileChooser.setDialogTitle("Delete file(s)");
        fileChooser.setMultiSelectionEnabled(true);

        // Show dialog
        int result = fileChooser.showDialog(MainWindow.getInstance(), "Delete");

        // Do something
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            if (Settings.getWarnOverwriteFile() == Settings.YES) {
                // Construct message
                StringBuilder message;
                String title;
                if (files.length == 1) {
                    title = "Delete file?";
                    message = new StringBuilder("Are you sure you want to delete the file \"" + files[0] + "\"?");
                } else {
                    title = "Delete files?";
                    message = new StringBuilder("Are you sure you want to delete the following files?");
                    for (File file : files) message.append("\n").append(file);
                }

                // Show dialog
                result = JOptionPane.showConfirmDialog(
                        MainWindow.getInstance(),
                        message.toString(),
                        title,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                // Delete files
                if (result == JOptionPane.YES_OPTION) {
                    for (File file : files) {
                        //noinspection ResultOfMethodCallIgnored
                        file.delete();
                    }
                    SavegameModel.updateQuickLoad();
                }
            } else {
                // Delete files
                for (File file : files) {
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                }
                SavegameModel.updateQuickLoad();
            }
        }
    }
}
