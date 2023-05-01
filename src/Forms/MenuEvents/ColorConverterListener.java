package Forms.MenuEvents;

import Forms.CustomComponents.ColorConvertingWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorConverterListener implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = new ColorConvertingWindow();
        frame.validate();
        frame.setVisible(true);
    }
}
