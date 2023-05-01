package Forms.MenuButtonEvent;

import Forms.MainCanvas;

import java.awt.event.ActionListener;

public abstract class CanvasEvent implements ActionListener {

    protected MainCanvas canvas;

    public CanvasEvent(MainCanvas canvas){
        this.canvas = canvas;
    }


}


