package Forms.MenuEvents;

import Forms.MainCanvas;
import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;

public class ThicknessListener implements ChangeListener {

    MainCanvas canvas;

    public ThicknessListener(MainCanvas canvas){
        this.canvas = canvas;
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        JSpinner spinner = (JSpinner)event.getSource();
        canvas.setThickness(Integer.parseInt(spinner.getValue().toString()));
    }
}
