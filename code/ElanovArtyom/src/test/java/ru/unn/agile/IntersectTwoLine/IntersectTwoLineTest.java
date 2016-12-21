package ru.unn.agile.IntersectTwoLine;



import org.junit.Test;

import static org.junit.Assert.*;

public class IntersectTwoLineTest {

    private final double delta = 0.001;

    @Test
    public void canCreateLineSegmentWithInitialValues() {
        LineSegment2D lineSegment = new LineSegment2D(2.1, 3.2, 4.5);
        assertNotNull(lineSegment);
    }
    @Test
    public void canSetInitialAValue() {
        LineSegment2D lineSegment = new LineSegment2D(2.1, 3.2, 4.5);
        assertEquals(2.1, lineSegment.getA(), delta);
    }
    @Test
    public void canSetInitialBValue() {
        LineSegment2D lineSegment = new LineSegment2D(2.1, 3.2, 4.5);
        assertEquals(3.2, lineSegment.getB(), delta);
    }
    @Test
    public void canSetInitialCValue() {
        LineSegment2D lineSegment = new LineSegment2D(2.1, 3.2, 4.5);
        assertEquals(4.5, lineSegment.getC(), delta);
    }

    @Test
    public void canCreatePointIntersectTwoLineSegment() {
        LineSegment2D lineSegment1 = new LineSegment2D(3.1, -2.3, 3);
        LineSegment2D lineSegment2 = new LineSegment2D(-4.5, 5.6, -1);
        assertNotNull(lineSegment1.checkIntersection(lineSegment2));
    }

    @Test
    public void showThatLinesAreParallel() {
        LineSegment2D lineSegment1 = new LineSegment2D(2.1, 3.1, -1);
        LineSegment2D lineSegment2 = new LineSegment2D(4.2, 6.2, 7);
        assertEquals("Lines are parallel", lineSegment1.checkIntersection(lineSegment2));
    }
    @Test
    public void showThatLinesMatch() {
        LineSegment2D lineSegment1 = new LineSegment2D(2.1, 3.1, -1);
        LineSegment2D lineSegment2 = new LineSegment2D(4.2, 6.2, -2);
        assertEquals("Lines math", lineSegment1.checkIntersection(lineSegment2));
    }


}
