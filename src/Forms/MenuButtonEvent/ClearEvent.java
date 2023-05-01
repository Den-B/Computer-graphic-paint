package Forms.MenuButtonEvent;

import Forms.MainCanvas;

import java.awt.event.ActionEvent;

public class ClearEvent extends CanvasEvent {

    public ClearEvent(MainCanvas canvas) {
        super(canvas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.canvas.clearCanvas();
        this.canvas.repaint();
    }
}
