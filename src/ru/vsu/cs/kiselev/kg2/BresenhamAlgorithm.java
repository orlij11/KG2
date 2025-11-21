package ru.vsu.cs.kiselev.kg2;

import java.util.ArrayList;
import java.util.List;

public class BresenhamAlgorithm implements EllipseAlgorithm {

    @Override
    public List<Point> calculatePoints(EllipseParameters params) {
        List<Point> points = new ArrayList<>();

        int cx = (int) Math.round(params.getCenter().getX());
        int cy = (int) Math.round(params.getCenter().getY());
        int a = (int) Math.round(params.getA());
        int b = (int) Math.round(params.getB());

        long a2 = (long) a * a;
        long b2 = (long) b * b;
        long twoA2 = 2 * a2;
        long twoB2 = 2 * b2;

        int x = 0;
        int y = b;

        long changeX = b2 * (2 * x + 1);
        long changeY = a2 * (1 - 2 * y);
        long p = 4 * b2 - 4 * a2 * b + a2;

        long stoppingX = twoB2 * x;
        long stoppingY = twoA2 * y;

        while (stoppingX <= stoppingY) {
            plot4Points(points, cx, cy, x, y);

            x++;
            stoppingX += twoB2;

            if (p < 0) {
                p += 4 * b2 * (2 * x + 1);
            } else {
                y--;
                stoppingY -= twoA2;
                p += 4 * b2 * (2 * x + 1) + 4 * a2 * (1 - 2 * y);
            }
        }

        p = 4 * b2 * x * x + 4 * b2 * x + b2 +
                4 * a2 * y * y - 8 * a2 * y + 4 * a2 -
                4 * a2 * b2;

        while (y >= 0) {
            plot4Points(points, cx, cy, x, y);

            y--;
            stoppingY -= twoA2;

            if (p > 0) {
                p += 4 * a2 * (1 - 2 * y);
            } else {
                x++;
                stoppingX += twoB2;
                p += 4 * a2 * (1 - 2 * y) + 4 * b2 * (2 * x + 1);
            }
        }

        return points;
    }

    private void plot4Points(List<Point> points, int cx, int cy, int x, int y) {
        points.add(new Point(cx + x, cy + y));
        points.add(new Point(cx - x, cy + y));
        points.add(new Point(cx + x, cy - y));
        points.add(new Point(cx - x, cy - y));
    }

    @Override
    public String getName() {
        return "Bresenham Algorithm (Integer)";
    }
}