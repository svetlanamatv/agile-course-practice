package ru.unn.agile.triangle.model;

import java.text.MessageFormat;

public class Circle {
    private final Point2D center;
    private final double radius;

    public Circle(final Point2D center, final double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point2D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{{0}, {1}}", center, radius);
    }
}
