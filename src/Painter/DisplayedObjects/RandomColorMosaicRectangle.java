package Painter.DisplayedObjects;

import Helpers.MosaicPattern;

import java.awt.*;
import java.util.ArrayList;

public class RandomColorMosaicRectangle extends MosaicRectangle{

    private final ArrayList<ArrayList<Color>> generatedColors = new ArrayList<ArrayList<Color>>();
    private int generatedRows = 0;
    private int generatedColumns = 0;


    public RandomColorMosaicRectangle(MosaicPattern pattern, Point initialPoint) {
        super(pattern, initialPoint);
    }

    private void generateRectangle(int width, int height){

        Color[] myColors = pattern.getColors();
        while(generatedColors.size() < height){
            generatedColors.add(new ArrayList<>());
        }

        for(ArrayList<Color> list: generatedColors){
            while(list.size() < width){
                list.add(myColors[(int) (Math.random() * (myColors.length))]);
            }
        }
        generatedRows = height;
        generatedColumns = width;
    }


    @Override
    protected Color getColor(int index) {
        int width = (int) Math.ceil((double)((endPoint.x - startPoint.x))/pattern.getSize());
        int height = (int) Math.ceil((double)(endPoint.y - startPoint.y)/pattern.getSize());
        if(generatedRows < height || generatedColumns < width){
            generateRectangle(width,height);
        }
        int row = width == 0 ? 0 : index/width;
        int column = width == 0 ? 0 :index%width;
        return generatedColors.get(row).get(column);
    }

    @Override
    public void fixed() {
    }
}
