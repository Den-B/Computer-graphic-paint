package Painter.DisplayedObjects;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Line extends LineObject{

    private Point startPoint;
    private Point endPoint;

    public Line(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Line(Point startPoint, Point endPoint, Color borderColor, int thickness) {
        super(borderColor,thickness);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    @Override
    public void initCoordinates(MouseEvent event) {
        this.startPoint = event.getPoint();
        this.endPoint = event.getPoint();
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }

    @Override
    public void change(MouseEvent event) {
        this.endPoint = event.getPoint();
    }

    @Override
    public void fixed() {

    }

}



