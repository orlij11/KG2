package ru.vsu.cs.kiselev.kg2;

import java.util.ArrayList;
import java.util.List;

public class ParametricAlgorithm implements EllipseAlgorithm {
    private final double step;

    public ParametricAlgorithm(double step) {
        this.step = step;
    }

    public ParametricAlgorithm() {
        this(0.01); // default step
    }

    @Override
    public List<Point> calculatePoints(EllipseParameters params) {
        List<Point> points = new ArrayList<>();
        Point center = params.getCenter();
        double a = params.getA();
        double b = params.getB();

        for (double theta = 0; theta <= 2 * Math.PI; theta += step) {
            double x = center.getX() + a * Math.cos(theta);
            double y = center.getY() + b * Math.sin(theta);
            points.add(new Point(x, y));
        }

        double x = center.getX() + a * Math.cos(0);
        double y = center.getY() + b * Math.sin(0);
        points.add(new Point(x, y));

        return points;
    }

    @Override
    public String getName() {
        return "Parametric Algorithm";
    }
}