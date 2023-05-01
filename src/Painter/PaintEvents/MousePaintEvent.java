package Painter.PaintEvents;

import Helpers.MosaicPattern;
import Painter.DisplayedObjects.*;
import Painter.DisplayedObjects.Rectangle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.LinkedList;

public class MousePaintEvent implements MouseListener {

    private MotionPaintEvent motionPaintEvent;
    private final LinkedList<BufferedImage> images = new LinkedList<>();
    private String object = "Brush";
    private Color currentPenColor = Color.black;
    private Color currentFillColor;
    private int thickness = 4;
    private DisplayedObject operationObject;
    private MosaicPattern pattern;
    private int indexOfCurrentImage = 0;
    private final int maxNumberOfImages = 10;
    private boolean isEmpty = true;


    public MousePaintEvent(int width, int height){
        Color[] mosaicColors = {
                Color.WHITE,
                new Color(76, 82, 224),
                new Color(224, 206, 130),
                new Color(50, 54, 148)
        };
        pattern = new MosaicPattern(mosaicColors,thickness);
        images.add(createEmptyImage(width, height));

    }

    private BufferedImage copyImage(BufferedImage image){
        return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
    }

    private DisplayedObject getObject(MouseEvent event){
        switch(object){
            case "Line":
                return new Line(event.getPoint(),event.getPoint(),currentPenColor, thickness);
            case "Rectangle":
                return new Rectangle(event.getPoint(),event.getPoint(),this.currentPenColor,this.currentFillColor,this.thickness);
            case "Mosaic":
                return new MosaicRectangle(pattern,event.getPoint());
            case "Random mosaic":
                return new RandomColorMosaicRectangle(pattern,event.getPoint());
            case "Brush":
            default:
                return new Brush(event.getPoint(),currentPenColor, thickness);
        }
    }

    private void setRenderingHints(Graphics2D graphics){
        graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    public String getDisplayedObject() {
        return object;
    }

    public void setThickness(int thickness){
        this.thickness = thickness;
    }

    public void setPenColor(Color color){
        this.currentPenColor = color;
    }

    public void setFillColor(Color color){
        this.currentFillColor = color;
    }

    private void paintImage(Graphics graphics, BufferedImage image){
        graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return true;
            }
        });
    }

    private BufferedImage getCurrentImage(){
        return images.get(indexOfCurrentImage);
    }

    public void resize(Dimension dimension){
        BufferedImage newImage = createEmptyImage(dimension.width, dimension.height);
        paintImage(newImage.createGraphics(), getCurrentImage());
        addImage(newImage);
    }

    public void paint(Graphics graphics){

        BufferedImage image =  getCurrentImage();

        if(operationObject != null){
            BufferedImage replica = copyImage(image);
            Graphics2D imageGraphics = replica.createGraphics();
            setRenderingHints(imageGraphics);
            operationObject.paint(imageGraphics);
            paintImage(graphics, replica);
        }
        else paintImage(graphics, image);

    }

    public MosaicPattern getMosaicPattern(){
        return this.pattern;
    }

    public void setMosaicPattern(MosaicPattern pattern){
        this.pattern = pattern;
    }

    private BufferedImage createEmptyImage(int width, int height){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = image.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0, 0 ,width, height);
        return image;
    }


    private void addImage(BufferedImage image){
        while(indexOfCurrentImage != images.size() - 1){
            images.removeLast();
        }
        images.add(image);
        while(images.size() > maxNumberOfImages){
            images.removeFirst();
        }
        goForward();
    }

    private void addObject(){
        if(operationObject != null){
            BufferedImage newImage = copyImage(getCurrentImage());
            Graphics2D graphics2D = newImage.createGraphics();
            setRenderingHints(graphics2D);
            operationObject.paint(graphics2D);
            addImage(newImage);
            operationObject = null;
            isEmpty = false;
        }
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void addNewImage(BufferedImage image){
        addImage(image);
        isEmpty = false;
    }

    public void clearCanvas(int width, int height){
        if(!isEmpty){
            addImage(createEmptyImage(width, height));
            isEmpty = true;
        }
    }

    public Dimension goBack(){
       if(indexOfCurrentImage > 0){
           indexOfCurrentImage--;
       }
        return getCurrentSize();
    }

    public Dimension goForward(){
        if(indexOfCurrentImage < images.size() - 1){
            indexOfCurrentImage++;
        }
        return getCurrentSize();
    }

    public Dimension getCurrentSize(){
        BufferedImage image = getCurrentImage();
        return new Dimension(image.getWidth(), image.getHeight());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        operationObject =  getObject(event);
        motionPaintEvent = new MotionPaintEvent(operationObject, event.getComponent().getGraphics());
        Component canvas = event.getComponent();
        canvas.addMouseMotionListener(motionPaintEvent);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Component canvas = e.getComponent();
        canvas.removeMouseMotionListener(motionPaintEvent);
        operationObject.fixed();
        addObject();
        canvas.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

record MotionPaintEvent(DisplayedObject displayedObject,
                        Graphics graphics) implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent event) {
        Component canvas = event.getComponent();
        displayedObject.change(event);
        canvas.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
