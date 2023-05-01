package Forms.MenuButtonEvent;

import Forms.MainCanvas;

import java.awt.event.ActionEvent;

public class GoBackListener extends CanvasEvent {

    public GoBackListener(MainCanvas canvas) {
        super(canvas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.canvas.goBack();
        this.canvas.repaint();
    }
}
