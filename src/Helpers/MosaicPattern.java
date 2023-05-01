package Helpers;

import java.awt.*;

public class MosaicPattern {

    private Color[] mosaicColors;
    private int mosaicSize;

    public MosaicPattern(Color[] colors, int size){
        this.mosaicColors = colors;
        this.mosaicSize = size;
    }

    public Color[] getColors() {
        return mosaicColors;
    }

    public int getSize() {
        return mosaicSize;
    }
}
