package ru.vsu.cs.kiselev.kg2;

import java.awt.Color;
import java.util.List;

public class EllipseExample {
    public static void main(String[] args) {

        EllipseDrawer drawer = new EllipseDrawer();
        drawer.setEllipse(new Point(100, 100), 80, 40);
        drawer.setAlgorithm(new BresenhamAlgorithm());
        drawer.setColor(Color.BLUE);
        drawer.setLineWidth(2);
        List<Point> points = drawer.getPoints();
        System.out.println("Generated " + points.size() + " points");
    }
}