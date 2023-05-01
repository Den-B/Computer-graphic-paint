package Forms.MenuEvents.Files;

import Forms.MainCanvas;
import Helpers.Files.BmpException;
import Helpers.Files.BmpFile;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class CreateNewBmpFileListener extends FileMenuListener{

    public CreateNewBmpFileListener(MainCanvas canvas) {
        super(canvas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            File file = this.openFile();
            if(file != null){
                if(file.exists())throw new BmpException("File with this name exists.");
                BmpFile bmpFile = new BmpFile(canvas.getBufferedImage());
                file.createNewFile();
                bmpFile.writeToFile(file);
            }
        } catch (BmpException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
