package ru.unn.agile.triangle.model;

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
}
