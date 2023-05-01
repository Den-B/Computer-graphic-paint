package Painter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BasicRasterAlgorithm {

    static int measure = 120;

    public static void paint(BufferedImage image, Double beginDegree, Double endDegree, Double step) {
        beginDegree = beginDegree%360;
        endDegree = endDegree%360;
        double smallRadius = 0;
        double bigRadius = 0;
        if(beginDegree > endDegree)endDegree+=360;
        Point centerPoint = new Point(image.getWidth()/2, image.getHeight()/2);
        for(Double currentDegree = beginDegree; currentDegree < endDegree; currentDegree+=step){
            double firstLength = firstFunction(currentDegree) * measure;
            double secondLength = secondFunction(currentDegree) * measure;
            if(smallRadius > secondLength || smallRadius == 0) smallRadius = secondLength;
            if(bigRadius < firstLength) bigRadius = firstLength;
            Point firstPoint = calculatePoint(currentDegree, centerPoint, firstLength);
            Point secondPoint = calculatePoint(currentDegree, centerPoint, secondLength);
            BresenhamAlgorithmForLine(firstPoint, secondPoint, image,Color.getHSBColor((float)(currentDegree/360),1,1));
        }
        BresenhamAlgorithmForCircle(centerPoint, (int)bigRadius, image, Color.BLACK);
        BresenhamAlgorithmForCircle(centerPoint, (int)smallRadius, image, Color.BLACK);
    }

    static private Point calculatePoint(double degree, Point centerPoint,double length) {
        int x = (int)Math.round(centerPoint.x + Math.cos(Math.toRadians(degree)) * length);
        int y = (int)Math.round(centerPoint.y - Math.sin(Math.toRadians(degree)) * length);
        return new Point(x,y);
    }


    static private double firstFunction(double degree){
        return (2 - Math.abs(Math.cos((Math.PI+10*Math.toRadians(degree))/4)))/2;
    }

    static double secondFunction(double degree){
        return (Math.tan((Math.PI*(2+Math.sin(5*Math.toRadians(degree))))/8))/5;
    }

    static void BresenhamAlgorithmForCircle(Point center, int radius, BufferedImage image, Color color){
        int i = 0;
        int j = radius;
        int delta = 1 - 2 * radius;
        int error;
        while(j >= i){
            image.setRGB(center.x + i, center.y + j, color.getRGB());
            image.setRGB(center.x + j, center.y + i, color.getRGB());
            image.setRGB(center.x + i, center.y - j, color.getRGB());
            image.setRGB(center.x + j, center.y - i, color.getRGB());
            image.setRGB(center.x - i, center.y - j, color.getRGB());
            image.setRGB(center.x - j, center.y - i, color.getRGB());
            image.setRGB(center.x - i, center.y + j, color.getRGB());
            image.setRGB(center.x - j, center.y + i, color.getRGB());
            error = 2 * (delta + j) - 1;
            if ((delta < 0) && (error <= 0)){
                delta += 2 * ++i + 1;
                continue;
            }
            if ((delta > 0) && (error > 0)){
                delta -= 2 * --j + 1;
                continue;
            }
            delta += 2 * (++i - --j);
        }

    }

    static void BresenhamAlgorithmForLine(Point fromPoint, Point toPoint, BufferedImage image, Color color){
        int xLength = Math.abs(toPoint.x - fromPoint.x);
        int yLength = Math.abs(toPoint.y - fromPoint.y);
        int signX = fromPoint.x < toPoint.x ? 1 : -1;
        int signY = fromPoint.y < toPoint.y ? 1 : -1;
        int error = xLength - yLength;
        image.setRGB(toPoint.x ,toPoint.y, color.getRGB());
        int x = fromPoint.x;
        int y = fromPoint.y;
        while(x != toPoint.x || y != toPoint.y){
            image.setRGB(x, y, color.getRGB());
            int error2 = error*2;
            if(error2 > -yLength){
                error -= yLength;
                x += signX;
            }
            if(error2 < xLength){
                error += xLength;
                y += signY;
            }
        }

    }
}
