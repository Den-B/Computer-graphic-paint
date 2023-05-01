package Forms.MenuButtonEvent;

import Forms.MainCanvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PenColorListener extends ColorListener{

    public PenColorListener(MainCanvas canvas){
        super(canvas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.createChooserDialog(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setPenColor(getColor());
            }
        },null);
    }
}
