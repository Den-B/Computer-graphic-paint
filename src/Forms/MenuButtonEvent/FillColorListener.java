package Forms.MenuButtonEvent;

import Forms.MainCanvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FillColorListener extends ColorListener{

    public FillColorListener(MainCanvas canvas){
        super(canvas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.createChooserDialog(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setFillColor(getColor());
            }
        },null);
    }
}
