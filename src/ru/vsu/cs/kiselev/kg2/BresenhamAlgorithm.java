package ru.vsu.cs.kiselev.kg2;

import java.util.ArrayList;
import java.util.List;

public class BresenhamAlgorithm implements EllipseAlgorithm {

    @Override
    public List<Point> calculatePoints(EllipseParameters params) {
        List<Point> points = new ArrayList<>();

        // Получаем центры и радиусы как целые числа
        int cx = (int) Math.round(params.getCenter().getX());
        int cy = (int) Math.round(params.getCenter().getY());
        int a = (int) Math.round(params.getA());
        int b = (int) Math.round(params.getB());

        // Используем long для квадратов радиусов, чтобы избежать переполнения (overflow)
        // при больших размерах эллипса (например, a=400 -> a^2=160000)
        long a2 = (long) a * a;
        long b2 = (long) b * b;
        long twoA2 = 2 * a2;
        long twoB2 = 2 * b2;

        // --- Регион 1 (где наклон касательной < 1) ---
        int x = 0;
        int y = b;

        long changeX = b2 * (2 * x + 1);
        long changeY = a2 * (1 - 2 * y);

        // Начальное значение параметра принятия решения p1.
        // Формула: p1 = b^2 - a^2*b + 0.25*a^2
        // Умножаем все на 4, чтобы избавиться от дроби 0.25 -> p = 4*b^2 - 4*a^2*b + a^2
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

        // --- Регион 2 (где наклон касательной > 1) ---
        // Пересчитываем параметр p для региона 2.
        // Начальные координаты (x, y) берем из конца предыдущего цикла.
        // Формула p2 = b^2(x+0.5)^2 + a^2(y-1)^2 - a^2b^2
        // Также домножаем на 4 для целочисленности.

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