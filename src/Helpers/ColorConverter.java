package Helpers;

import java.util.HashMap;
import java.util.Map;

public class ColorConverter {

    static public Map<String, Double> convertRgbToXyz(int red, int green, int blue){
        Map<String, Double> xyzMap = new HashMap<>();
        try {
            double[] rgbToXyzInitArray= {  0.5767309,  0.1855540,  0.1881852,
                                           0.2973769,  0.6273491,  0.0752741,
                                           0.0270343,  0.0706872,  0.9911085 };
            DecimalMatrix XyzToRgbConversionMatrix = new DecimalMatrix(3,3, rgbToXyzInitArray);
            double[] rgbInitArray = { red, green, blue };
            for(int color = 0; color < rgbInitArray.length; color++){
                double colorValue = rgbInitArray[color];
                colorValue /=255.0;
                colorValue = colorValue > 0.04045 ? (Math.pow((colorValue+0.055), 2.4) / 1.055) : colorValue/12.92;
                colorValue*=100;
                rgbInitArray[color] = colorValue;
            }
            DecimalMatrix rgbMatrix = new DecimalMatrix(3, 1, rgbInitArray);
            DecimalMatrix resultMatrix =  XyzToRgbConversionMatrix.multiplication(rgbMatrix);
            xyzMap.put("X",Math.min(resultMatrix.getVectorValue(0),95.047));
            xyzMap.put("Y",Math.min(resultMatrix.getVectorValue(1), 100));
            xyzMap.put("Z",Math.min(resultMatrix.getVectorValue(2),  108.883));
        } catch (MatrixException e) {
            e.printStackTrace();
        }
        return xyzMap;
    }

    static public Map<String, Double> convertXyzToRgb(double x, double y, double z){
        Map<String, Double> rgbMap = new HashMap<>();
        try {
            double[] rgbToXyzInitArray= {   2.0413690, -0.5649464, -0.3446944,
                                           -0.9692660,  1.8760108,  0.0415560,
                                            0.0134474, -0.1183897,  1.0154096 };
            DecimalMatrix XyzToRgbConversionMatrix = new DecimalMatrix(3,3, rgbToXyzInitArray);
            double[] xyzInitArray = { x , y, z };
            for(int color = 0; color < xyzInitArray.length; color++){
                double colorValue = xyzInitArray[color];
                colorValue /= 100.0;
                xyzInitArray[color] = colorValue;
            }

            DecimalMatrix xyzMatrix = new DecimalMatrix(3, 1, xyzInitArray);
            DecimalMatrix resultMatrix =  XyzToRgbConversionMatrix.multiplication(xyzMatrix);

            for(int colorCharacteristic = 0; colorCharacteristic < 3; colorCharacteristic++){
                double value = resultMatrix.getVectorValue(colorCharacteristic);
                value = value > 0.0031308 ? (Math.pow((value), 0.41666) * 1.055) - 0.055 : value * 12.92;
                value *= 255;
                resultMatrix.setVectorValue(colorCharacteristic,value);
            }

            rgbMap.put("R",Math.min(resultMatrix.getVectorValue(0),255));
            rgbMap.put("G",Math.min(resultMatrix.getVectorValue(1), 255));
            rgbMap.put("B",Math.min(resultMatrix.getVectorValue(2), 255));

        } catch (MatrixException e) {
            e.printStackTrace();
        }
        return rgbMap;
    }
}



