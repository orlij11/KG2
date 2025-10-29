package ru.vsu.cs.kiselev.kg2;

import java.util.ArrayList;
import java.util.List;

public class BresenhamAlgorithm implements EllipseAlgorithm {

    @Override
    public List<Point> calculatePoints(EllipseParameters params) {
        List<Point> points = new ArrayList<>();
        Point center = params.getCenter();
        int a = (int) Math.round(params.getA());
        int b = (int) Math.round(params.getB());
        int cx = (int) Math.round(center.getX());
        int cy = (int) Math.round(center.getY());

        int x = 0;
        int y = b;
        int a2 = a * a;
        int b2 = b * b;
        int twoA2 = 2 * a2;
        int twoB2 = 2 * b2;
        int p;
        int px = 0;
        int py = twoA2 * y;
        plot4Points(points, cx, cy, x, y);

        p = (int) (b2 - a2 * b + 0.25 * a2);
        while (px < py) {
            x++;
            px += twoB2;
            if (p < 0) {
                p += b2 + px;
            } else {
                y--;
                py -= twoA2;
                p += b2 + px - py;
            }
            plot4Points(points, cx, cy, x, y);
        }

        p = (int) (b2 * (x + 0.5) * (x + 0.5) + a2 * (y - 1) * (y - 1) - a2 * b2);
        while (y > 0) {
            y--;
            py -= twoA2;
            if (p > 0) {
                p += a2 - py;
            } else {
                x++;
                px += twoB2;
                p += a2 - py + px;
            }
            plot4Points(points, cx, cy, x, y);
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
        return "Bresenham Algorithm";
    }
}
