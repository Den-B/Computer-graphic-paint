package Forms.MenuButtonEvent;


import Forms.MainCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ColorListener extends CanvasEvent {

    private JColorChooser chooser = new JColorChooser(Color.BLACK);

    public ColorListener(MainCanvas canvas){
        super(canvas);
    }

    protected void createChooserDialog(ActionListener okListener, ActionListener cancelListener){
        JDialog dialog = JColorChooser.createDialog(new JFrame(), "Choose color", true, chooser, okListener , cancelListener);
        dialog.setVisible(true);
    }

    protected Color getColor(){
        return chooser.getColor();
    }

}
