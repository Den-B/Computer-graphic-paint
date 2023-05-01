package Painter.DisplayedObjects;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Rectangle extends ClosedObject{

    protected Point startPoint;
    protected Point endPoint;
    protected Point initializedPoint;

    public Rectangle(Point startPoint, Point endPoint, Color borderColor, Color fillColor, int thickness){
        super(borderColor, fillColor, thickness);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.initializedPoint = startPoint;
    }

    @Override
    public void initCoordinates(MouseEvent event) {
        this.startPoint = event.getPoint();
        this.endPoint = event.getPoint();
        this.initializedPoint = event.getPoint();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.drawRect(startPoint.x, startPoint.y, endPoint.x - startPoint.x, endPoint.y - startPoint.y);
        if(this.getInnerColor() != null){
            Color oldColor = graphics.getColor();
            graphics.setColor(this.getInnerColor());
            graphics.fillRect(startPoint.x, startPoint.y, endPoint.x - startPoint.x, endPoint.y - startPoint.y);
            graphics.setColor(oldColor);
        }

    }

    @Override
    public void change(MouseEvent event) {
        Point currentPoint = event.getPoint();
        Point newStartPoint = null;
        Point newEndPoint = null;
        // Изменение точек для прорисовки прямоугольника
        if(this.initializedPoint.y > currentPoint.y){
            if(this.initializedPoint.x > currentPoint.x){
                newStartPoint = currentPoint;
                newEndPoint = initializedPoint;
            }
            else {
                newStartPoint = new Point(initializedPoint.x,currentPoint.y);
                newEndPoint = new Point(currentPoint.x,initializedPoint.y);
            }
        }
        else{
            if(this.initializedPoint.x > currentPoint.x){
                newStartPoint = new Point(currentPoint.x,initializedPoint.y);
                newEndPoint = new Point(initializedPoint.x,currentPoint.y);
            }
            else {
                newEndPoint = currentPoint;
                newStartPoint = startPoint;
            }
        }
        this.startPoint = newStartPoint;
        this.endPoint = newEndPoint;
    }

    @Override
    public void fixed() {

    }
}
