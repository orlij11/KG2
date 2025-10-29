package ru.vsu.cs.kiselev.kg2;

public class EllipseParameters {
    private Point center;
    private double a;
    private double b;

    public EllipseParameters(Point center, double a, double b) {
        if (a <= 0 || b <= 0) {
            throw new IllegalArgumentException("Semi-axes must be positive");
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

    public double getBoundingX() {
        return center.getX() - a;
    }

    public double getBoundingY() {
        return center.getY() - b;
    }

    public double getBoundingWidth() {
        return a * 2;
    }

    public double getBoundingHeight() {
        return b * 2;
    }
}