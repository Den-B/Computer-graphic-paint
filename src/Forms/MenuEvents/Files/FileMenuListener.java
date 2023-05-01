package Forms.MenuEvents.Files;

import Forms.MainCanvas;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;

public abstract class FileMenuListener implements ActionListener {

    protected MainCanvas canvas;

    public FileMenuListener(MainCanvas canvas){
        this.canvas = canvas;
    }


    protected File openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int ret = fileChooser.showDialog(null, "Open file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }else return null;
    }
}
