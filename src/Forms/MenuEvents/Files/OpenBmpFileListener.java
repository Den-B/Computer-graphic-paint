package Forms.MenuEvents.Files;

import Forms.MainCanvas;
import Helpers.Files.BmpException;
import Helpers.Files.BmpFile;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OpenBmpFileListener extends FileMenuListener{
    public OpenBmpFileListener(MainCanvas canvas) {
        super(canvas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            File file = this.openFile();
            if(file != null){
                BmpFile bmpFile = new BmpFile(file);
                BufferedImage image = bmpFile.getRgbImage();
                canvas.addImage(image);
            }
        } catch (BmpException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
