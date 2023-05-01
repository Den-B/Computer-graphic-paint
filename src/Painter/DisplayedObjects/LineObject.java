package Painter.DisplayedObjects;

import java.awt.*;

public abstract class LineObject implements DisplayedObject{

    private Color penColor = Color.BLACK;
    private int LineThickness = 4;

    public LineObject(){
    }

    public LineObject(Color penColor, int LineThickness){
        this.penColor = penColor;
        this.LineThickness = LineThickness;
    }

    @Override
    public void paint(Graphics graphics) {

        graphics.setColor(penColor);
        try{
            ((Graphics2D)graphics).setStroke(new BasicStroke(LineThickness));
        }
        catch(ClassCastException exception){
            System.out.println("Can't change thickness of pen.");
        }

    }

    public void setBorderColor(Color borderColor) {
        this.penColor = borderColor;
    }

    public void setLineThickness(int lineThickness) {
        LineThickness = lineThickness;
    }

    public Color getBorderColor() {
        return penColor;
    }

    public int getLineThickness() {
        return LineThickness;
    }
}
