package Forms.MenuEvents;

import Helpers.MosaicPattern;
import Forms.CustomComponents.MosaicPatternChooser;
import Forms.MainCanvas;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MosaicListener extends ToolListener{

    private JFrame owner;

    public MosaicListener(MainCanvas canvas, JFrame owner) {
        super(canvas);
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        MosaicPattern pattern = canvas.getMosaicPattern();
        MosaicPatternChooser chooser = new MosaicPatternChooser(pattern, ((JMenuItem)event.getSource()).getText());
        JDialog dialog = chooser.createDialog(owner,  canvas,true);
        dialog.validate();
        dialog.setVisible(true);
    }
}
