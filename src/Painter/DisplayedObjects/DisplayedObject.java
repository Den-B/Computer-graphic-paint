package Painter.DisplayedObjects;

import jdk.jfr.Description;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface DisplayedObject {


    void initCoordinates(MouseEvent event);
    // Метод отвечающий за прорисовку объекта
    void paint(Graphics graphics);
    // Метод отвечающий за изменение объекта при перемещении мыши в режиме Drag&Drop
    void change(MouseEvent event);
    // Объект зафиксирован
    void fixed();

}
