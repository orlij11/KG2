package ru.vsu.cs.kiselev.kg2;

import java.awt.*;

public class ControlPoint {
    private Point position;
    private final ControlPointType type;
    private static final int SIZE = 8;
    private static final int HALF_SIZE = SIZE / 2;

    public enum ControlPointType {
        CENTER, RIGHT, BOTTOM, LEFT, TOP
    }

    public ControlPoint(Point position, ControlPointType type) {
        this.position = position;
        this.type = type;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public ControlPointType getType() {
        return type;
    }

    public boolean contains(Point p) {
        double dx = p.getX() - position.getX();
        double dy = p.getY() - position.getY();
        return Math.abs(dx) <= HALF_SIZE && Math.abs(dy) <= HALF_SIZE;
    }

    public void draw(Graphics2D g2d) {
        int x = (int) Math.round(position.getX() - HALF_SIZE);
        int y = (int) Math.round(position.getY() - HALF_SIZE);

        switch (type) {
            case CENTER:
                g2d.setColor(Color.RED);
                g2d.fillRect(x, y, SIZE, SIZE);
                break;
            case RIGHT:
            case LEFT:
                g2d.setColor(Color.BLUE);
                g2d.fillOval(x, y, SIZE, SIZE);
                break;
            case TOP:
            case BOTTOM:
                g2d.setColor(Color.GREEN);
                g2d.fillOval(x, y, SIZE, SIZE);
                break;
        }

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, SIZE, SIZE);
    }
}