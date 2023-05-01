package Forms.MenuEvents;

import Forms.MainCanvas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolListener implements ActionListener {

    protected final MainCanvas canvas;

    public ToolListener(MainCanvas canvas){
        this.canvas = canvas;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        canvas.setDisplayedObject(((JMenuItem)event.getSource()).getText());
    }
}
