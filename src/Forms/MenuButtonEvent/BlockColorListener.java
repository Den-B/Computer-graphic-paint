package Forms.MenuButtonEvent;

import Forms.MainCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockColorListener extends ColorListener{

    private final Color[] colors;
    private final int workIndex;
    private final JPanel displayedPanel;

    public BlockColorListener(MainCanvas canvas, Color[] colors, int workIndex, JPanel displayedPanel) {
        super(canvas);
        this.colors = colors;
        this.workIndex = workIndex;
        this.displayedPanel = displayedPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.createChooserDialog(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colors[workIndex] = getColor();
                displayedPanel.setBackground(getColor());
            }
        }, null);

    }
}
