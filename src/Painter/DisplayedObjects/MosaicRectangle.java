package Painter.DisplayedObjects;

import Helpers.MosaicPattern;

import java.awt.*;

public class MosaicRectangle extends Rectangle{

    protected final MosaicPattern pattern;

    public MosaicRectangle(MosaicPattern pattern, Point initialPoint){
        super(initialPoint,initialPoint,null,null,0);
        this.pattern = pattern;
    }

    @Override
    public void paint(Graphics graphics) {
        Point point = new Point(this.startPoint);
        Color oldColor = graphics.getColor();
        for(int colorIndex = 0;point.y < endPoint.y; point.y += pattern.getSize()){
            for(;point.x < endPoint.x; point.x += pattern.getSize(), colorIndex++){
                int endX, endY;
                graphics.setColor(getColor(colorIndex));
                endX = Math.min(point.x + pattern.getSize(), endPoint.x);
                endY = Math.min(point.y + pattern.getSize(), endPoint.y);
                graphics.fillRect(point.x, point.y, endX - point.x, endY - point.y);
            }
            point.x = this.startPoint.x;
        }
        graphics.setColor(oldColor);
    }

    protected Color getColor(int index){
        Color[] colors = pattern.getColors();
        return colors[index % colors.length];
    }

}
