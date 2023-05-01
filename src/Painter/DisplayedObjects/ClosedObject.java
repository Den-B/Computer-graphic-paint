package Painter.DisplayedObjects;

import java.awt.*;

public abstract class ClosedObject extends LineObject{

    // Внутрений цвет замкнутого объекта
    protected Color fillColor;

    public ClosedObject(Color borderColor, Color fillColor, int thickness){
        super(borderColor, thickness);
        this.fillColor = fillColor;
    }

    public Color getInnerColor() {
        return fillColor;
    }

    public void setInnerColor(Color innerColor) {
        this.fillColor = innerColor;
    }
}
