package ru.unn.agile.IntersectTwoLine;



import org.junit.Test;

import static org.junit.Assert.*;

public class IntersectTwoLineTest {

    private final double delta = 0.001;

    @Test
    public void canCreateLineSegmentWithInitialValues() {
        LineSegment2D LineSegment = new LineSegment2D(2.1, 3.2, 4.5);
        assertNotNull(LineSegment);
    }
    @Test
    public void canSetInitialAValue() {
        LineSegment2D LineSegment = new LineSegment2D(2.1, 3.2, 4.5);
        assertEquals(2.1, LineSegment.getA(), delta);
    }
    @Test
    public void canSetInitialBValue() {
        LineSegment2D LineSegment = new LineSegment2D(2.1, 3.2, 4.5);
        assertEquals(3.2, LineSegment.getB(), delta);
    }
    @Test
    public void canSetInitialCValue() {
        LineSegment2D LineSegment = new LineSegment2D(2.1, 3.2, 4.5);
        assertEquals(4.5, LineSegment.getC(), delta);
    }

    @Test
    public void canCreatePointIntersectTwoLineSegment() {
        LineSegment2D LineSegment1 = new LineSegment2D(3.1, -2.3, 3);
        LineSegment2D LineSegment2 = new LineSegment2D(-4.5, 5.6, -1);
        assertNotNull(LineSegment1.checkIntersection(LineSegment2));
    }

    @Test
    public void showThatLinesAreParallel() {
        LineSegment2D LineSegment1 = new LineSegment2D(2.1, 3.1, -1);
        LineSegment2D LineSegment2 = new LineSegment2D(4.2, 6.2, 7);
        assertEquals( "Lines are parallel", LineSegment1.checkIntersection(LineSegment2));
    }
    @Test
    public void showThatLinesMatch() {
        LineSegment2D LineSegment1 = new LineSegment2D(2.1, 3.1, -1);
        LineSegment2D LineSegment2 = new LineSegment2D(4.2, 6.2, -2);
        assertEquals( "Lines math", LineSegment1.checkIntersection(LineSegment2));
    }


}
