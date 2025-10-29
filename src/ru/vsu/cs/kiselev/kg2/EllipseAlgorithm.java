package ru.vsu.cs.kiselev.kg2;

import java.util.List;

public interface EllipseAlgorithm {
    List<Point> calculatePoints(EllipseParameters params);
    String getName();
}