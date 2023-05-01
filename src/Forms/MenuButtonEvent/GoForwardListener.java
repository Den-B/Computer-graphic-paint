package Forms.MenuButtonEvent;

import Forms.MainCanvas;

import java.awt.event.ActionEvent;

public class GoForwardListener extends CanvasEvent {

    public GoForwardListener(MainCanvas canvas) {
        super(canvas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.canvas.goForward();
        this.canvas.repaint();
    }
}