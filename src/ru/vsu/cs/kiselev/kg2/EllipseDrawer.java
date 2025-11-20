package ru.vsu.cs.kiselev.kg2;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EllipseDrawer {
    private EllipseParameters params;
    private EllipseAlgorithm algorithm;
    private Color color;
    private int lineWidth;
    private List<ControlPoint> controlPoints;
    private boolean showControlPoints = false;

    public EllipseDrawer() {
        this.algorithm = new BresenhamAlgorithm();
        this.color = Color.BLACK;
        this.lineWidth = 1;
        this.controlPoints = new ArrayList<>();
    }

    public void setEllipse(Point center, double a, double b) {
        this.params = new EllipseParameters(center, a, b);
        updateControlPoints();
    }

    public void setEllipse(double x, double y, double width, double height) {
        this.params = EllipseParameters.fromBoundingBox(x, y, width, height);
        updateControlPoints();
    }

    public void setAlgorithm(EllipseAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setShowControlPoints(boolean show) {
        this.showControlPoints = show;
    }

    public List<Point> getPoints() {
        if (params == null) {
            throw new IllegalStateException("Ellipse parameters not set");
        }
        return algorithm.calculatePoints(params);
    }

    public List<ControlPoint> getControlPoints() {
        return controlPoints;
    }

    public ControlPoint getControlPointAt(Point p) {
        for (ControlPoint cp : controlPoints) {
            if (cp.contains(p)) {
                return cp;
            }
        }
        return null;
    }

    public void updateControlPoints() {
        if (params == null) return;

        controlPoints.clear();
        Point center = params.getCenter();
        double a = params.getA();
        double b = params.getB();

        controlPoints.add(new ControlPoint(center, ControlPoint.ControlPointType.CENTER));
        controlPoints.add(new ControlPoint(new Point(center.getX() + a, center.getY()),
                ControlPoint.ControlPointType.RIGHT));
        controlPoints.add(new ControlPoint(new Point(center.getX() - a, center.getY()),
                ControlPoint.ControlPointType.LEFT));
        controlPoints.add(new ControlPoint(new Point(center.getX(), center.getY() + b),
                ControlPoint.ControlPointType.BOTTOM));
        controlPoints.add(new ControlPoint(new Point(center.getX(), center.getY() - b),
                ControlPoint.ControlPointType.TOP));
    }

    public void moveControlPoint(ControlPoint cp, Point newPosition) {
        Point center = params.getCenter();

        switch (cp.getType()) {
            case CENTER:
                params.setCenter(newPosition);
                break;
            case RIGHT:
                params.setA(Math.abs(newPosition.getX() - center.getX()));
                break;
            case LEFT:
                params.setA(Math.abs(center.getX() - newPosition.getX()));
                break;
            case BOTTOM:
                params.setB(Math.abs(newPosition.getY() - center.getY()));
                break;
            case TOP:
                params.setB(Math.abs(center.getY() - newPosition.getY()));
                break;
        }

        updateControlPoints();
    }

    public void draw(Graphics2D g2d) {
        if (params == null) return;

        List<Point> points = getPoints();

        g2d.setColor(color);

        for (Point p : points) {
            g2d.fillRect((int)p.getX(), (int)p.getY(), lineWidth, lineWidth);
        }

        if (showControlPoints) {
            for (ControlPoint cp : controlPoints) {
                cp.draw(g2d);
            }
        }
    }

    public String getInfo() {
        if (params == null) return "No ellipse";
        return String.format("Center: %s, a=%.1f, b=%.1f",
                params.getCenter(), params.getA(), params.getB());
    }

    public String getAlgorithmInfo() {
        if (algorithm == null) {
            return "No algorithm set";
        }
        return algorithm.getName();
    }
}