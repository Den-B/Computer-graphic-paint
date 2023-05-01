package Painter.DisplayedObjects;
import java.awt.*;
import java.awt.event.MouseEvent;


public class Brush extends LineObject{

    private final PolyLine polyLine = new PolyLine();

    public Brush(Point beginPoint, Color borderColor, int thickness){
        super(borderColor,thickness);
        this.polyLine.addPoint(beginPoint);
    }
    public Brush(Point beginPoint){
        polyLine.addPoint(beginPoint);
    }

    @Override
    public void initCoordinates(MouseEvent event) {
        polyLine.addPoint(event.getPoint());
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.drawPolyline(polyLine.getXCoordinates(), polyLine.getYCoordinates(), polyLine.getNumberOfCoordinates());
    }

    @Override
    public void change(MouseEvent event) {
        polyLine.addPoint(event.getPoint());
    }

    @Override
    public void fixed() {

    }

}

class PolyLine{

    private int[] xCoordinates = new int[10];
    private int[] yCoordinates = new int[10];
    private int numberOfCoordinates = 0;

    private void allocationMemory(){
        long newSize = Math.round(xCoordinates.length * 1.5);
        int[] newXCoordinates = new int[Math.toIntExact(newSize)];
        int[] newYCoordinates = new int[Math.toIntExact(newSize)];
        for(int index = 0; index < numberOfCoordinates; index++){
            newXCoordinates[index] = xCoordinates[index];
            newYCoordinates[index] = yCoordinates[index];
        }
        this.xCoordinates = newXCoordinates;
        this.yCoordinates = newYCoordinates;
    }

    public void addPoint(Point point){
        if(numberOfCoordinates >= xCoordinates.length || numberOfCoordinates >= yCoordinates.length){
            this.allocationMemory();
        }
        this.xCoordinates[numberOfCoordinates] = point.x;
        this.yCoordinates[numberOfCoordinates] = point.y;
        numberOfCoordinates++;
    }

    public int[] getXCoordinates() {
        return xCoordinates;
    }

    public int[] getYCoordinates() {
        return yCoordinates;
    }

    public int getNumberOfCoordinates() {
        return numberOfCoordinates;
    }
}
