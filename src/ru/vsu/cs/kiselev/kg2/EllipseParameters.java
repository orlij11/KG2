package ru.vsu.cs.kiselev.kg2;

public class EllipseParameters {
    private Point center;
    private double a;
    private double b;

    public EllipseParameters(Point center, double a, double b) {
        if (a <= 1 || b <= 1) {
            throw new IllegalArgumentException("Радиус должен быть больше 0");
        }
        this.center = center;
        this.a = a;
        this.b = b;
    }

    public Point getCenter() {
        return center;
    }
    public void setCenter(Point center) {
        this.center = center;
    }
    public double getA() {
        return a;
    }
    public void setA(double a) {
        this.a = a;
    }
    public double getB() {
        return b;
    }
    public void setB(double b) {
        this.b = b;
    }
    public static EllipseParameters fromBoundingBox(double x, double y, double width, double height) {
        Point center = new Point(x + width / 2, y + height / 2);
        return new EllipseParameters(center, width / 2, height / 2);
    }
}