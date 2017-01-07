package ru.unn.agile.triangle.model;

import org.junit.Test;

import java.text.MessageFormat;

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

    @Test
    public void rightFormatOfToStringMethod() throws Exception {
        double expectX = 0.3;
        double expectY = 7.1;
        Point2D point = new Point2D(expectX, expectY);

        String pointStr = point.toString();

        assertEquals(pointStr, MessageFormat.format(
                "[{0}, {1}]", expectX, expectY));
    }
}
