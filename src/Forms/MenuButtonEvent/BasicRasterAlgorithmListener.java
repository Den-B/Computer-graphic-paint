package Forms.MenuButtonEvent;

import Forms.CustomComponents.BasicRasterAlgorithmWindow;
import Forms.MainCanvas;
import Painter.BasicRasterAlgorithm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class BasicRasterAlgorithmListener extends CanvasEvent {

    JFrame owner;

    public BasicRasterAlgorithmListener(MainCanvas canvas, JFrame owner) {
        super(canvas);
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BasicRasterAlgorithmWindow window = new BasicRasterAlgorithmWindow(owner,canvas, true);
        window.setVisible(true);
    }
}
