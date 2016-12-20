package ru.unn.agile.Triangle.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class WhenCalculatingInscribedOrCircumscribedCircle {
    private static final double DELTA = 0.001;
    private static final double NAN = Double.NaN;
    private static final Triangle SIMPLE_TRIANGLE = new Triangle(new Point2D(0, 0),
                new Point2D(0, 1),
                new Point2D(1, 0));
    private static final Triangle DIFFICULT_TRIANGLE = new Triangle(new Point2D(2, 0),
            new Point2D(-0.5, 0.4),
            new Point2D(0, -4));
    private static final Triangle DEGENERATE_TRIANGLE = new Triangle(new Point2D(2, 0),
            new Point2D(2, 0),
            new Point2D(0, 0));

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        List<Object[][]> list = Arrays.asList(
                getTestDataCircumscribedCircle(),
                getTestDataInscribedCircle()
        );

        List<Object[]> dataSet = new ArrayList<Object[]>();

        for (int i = 0; i < list.size(); i++) {
            Object[][] objects = list.get(0);
            for (int j = 0; j < objects[0].length; j++) {
                dataSet.add(objects[j]);
            }
        }

        return dataSet;
    }

    private static Object[][] getTestDataInscribedCircle() {
        return new Object[][]{
                {
                        getPoint(0.293, 0.293), 0.293, SIMPLE_TRIANGLE.getIncircle()
                },
                {
                        getPoint(0.579, -0.729), 0.944, DIFFICULT_TRIANGLE.getIncircle()
                },
                {
                        getPoint(2, 0), 0, DEGENERATE_TRIANGLE.getIncircle()
                }
        };
    }

    private static Object[][] getTestDataCircumscribedCircle() {
        return new Object[][]{
                {
                        getPoint(0.5, 0.5), 0.707, SIMPLE_TRIANGLE.getCircumcircle()
                },
                {
                        getPoint(0.442, -1.721), 2.321, DIFFICULT_TRIANGLE.getCircumcircle()
                },
                {
                        getPoint(NAN, NAN), NAN, DEGENERATE_TRIANGLE.getCircumcircle()
                }
        };
    }

    private static Point2D getPoint(final double x, final double y) {
        return new Point2D(x, y);
    }

    private Point2D expectCenter;
    private double expectRadius;
    private Circle actualCircle;

    public WhenCalculatingInscribedOrCircumscribedCircle(final Point2D expectCenter,
                                                         final double expectRadius,
                                                         final Circle actualCircle) {
        this.expectCenter = expectCenter;
        this.expectRadius = expectRadius;
        this.actualCircle = actualCircle;
    }

    @Test
    public void circleParametersAreCalculatedCorrectly() {
        double expectX = expectCenter.getX();
        double expectY = expectCenter.getY();

        Point2D actualCenter = actualCircle.getCenter();

        assertEquals(expectX, actualCenter.getX(), DELTA);
        assertEquals(expectY, actualCenter.getY(), DELTA);
        assertEquals(expectRadius, actualCircle.getRadius(), DELTA);
    }
}
