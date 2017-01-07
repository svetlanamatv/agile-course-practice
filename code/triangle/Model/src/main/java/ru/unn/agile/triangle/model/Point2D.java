package ru.unn.agile.triangle.model;

import java.text.MessageFormat;

public class Point2D {
    private final double x;
    private final double y;

    public Point2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[{0}, {1}]", x, y);
    }
}
