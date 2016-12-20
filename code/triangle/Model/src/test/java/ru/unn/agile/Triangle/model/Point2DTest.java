package ru.unn.agile.Triangle.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Point2DTest {
    private static final double DELTA = 0.001;

    @Test
    public void canCreatePoint2DWithInitialValues() {
        Point2D point = new Point2D(0, 0);

        assertNotNull(point);
    }

    @Test
    public void canSetInitialValueOfPoint() {
        double expectX = 0.3;
        double expectY = 7.1;
        Point2D point = new Point2D(expectX, expectY);

        assertEquals(expectX, point.getX(), DELTA);
        assertEquals(expectY, point.getY(), DELTA);
    }
}
