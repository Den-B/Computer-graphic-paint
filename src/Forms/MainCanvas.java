package Forms;
import Helpers.MosaicPattern;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import Painter.PaintEvents.MousePaintEvent;

public class MainCanvas extends JPanel {

    private final MousePaintEvent paintEvent;

    public MainCanvas(int width, int height) {
        super();
        this.setBackground(Color.WHITE);
        //this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(width, height));
        paintEvent = new MousePaintEvent(width, height);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        this.addMouseListener(paintEvent);
        this.revalidate();
    }

    public BufferedImage getBufferedImage(){
        BufferedImage result = this.getGraphicsConfiguration().createCompatibleImage(this.getWidth(),this.getHeight());
        Graphics2D graphics= (Graphics2D)result.getGraphics();
        graphics.translate(0,0);
        this.printAll(graphics);
        graphics.dispose();
        return result;
    }

    public void clearCanvas(){
        paintEvent.clearCanvas(this.getWidth(), this.getHeight());
    }

    public void addImage(BufferedImage image){
        this.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
        paintEvent.addNewImage(image);
        this.revalidate();
        this.repaint();
    }

    public void setPenColor(Color color){
        paintEvent.setPenColor(color);
    }

    public void setFillColor(Color color){
        paintEvent.setFillColor(color);
    }

    public BufferedImage getCopyOfCurrentImage(){
        return this.getBufferedImage();
    }

    public void goBack(){
        setPreferredSize(paintEvent.goBack());
        this.revalidate();
    }

    public void goForward(){
        setPreferredSize(paintEvent.goForward());
        this.revalidate();
    }

    public String getDisplayedObject(){
        return paintEvent.getDisplayedObject();
    }

    public void resizeScreen(Dimension screenSize){
        this.setPreferredSize(screenSize);
        paintEvent.resize(screenSize);
        this.revalidate();
    }

    public MosaicPattern getMosaicPattern(){
        return paintEvent.getMosaicPattern();
    }

    public void setDisplayedObject(String object){
        paintEvent.setObject(object);
    }

    public void setThickness(int thickness){
        paintEvent.setThickness(thickness);
    }

    public void setMosaicPattern(MosaicPattern pattern){
        paintEvent.setMosaicPattern(pattern);
    }


    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        paintEvent.paint(graphics);
        this.getGraphics().setColor(Color.black);
    }
}
