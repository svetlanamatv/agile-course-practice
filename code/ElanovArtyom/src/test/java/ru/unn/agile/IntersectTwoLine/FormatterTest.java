package ru.unn.agile.IntersectTwoLine;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by User on 020 20.12.16.
 */
public class FormatterTest {

    @Test
    public void canFormatToFormulaForDefaultLine(){
        LineSegment2D LineSegment = new LineSegment2D (2.1,3.1,4.1);
        assertEquals("2.1*x+3.1*y+4.1=0", LineSegment.toFormula());
    }
    @Test
    public void canFormatToFormulaWithAllNegativeArguments(){
        LineSegment2D LineSegment = new LineSegment2D (-2.1,-3.1,-4.1);
        assertEquals("-2.1*x-3.1*y-4.1=0", LineSegment.toFormula());

    }
    @Test
    public void canFormatToFormulaWithNegativeArgumentA(){
        LineSegment2D LineSegment = new LineSegment2D (-2.1,3.1,4.1);
        assertEquals("-2.1*x+3.1*y+4.1=0", LineSegment.toFormula());

    }
    @Test
    public void canFormatToFormulaWithNegativeArgumentB(){
        LineSegment2D LineSegment = new LineSegment2D (2.1,-3.1,4.1);
        assertEquals("2.1*x-3.1*y+4.1=0", LineSegment.toFormula());

    }
    @Test
    public void canFormatToFormulaWithNegativeArgumentC(){
        LineSegment2D LineSegment = new LineSegment2D (2.1,3.1,-4.1);
        assertEquals("2.1*x+3.1*y-4.1=0", LineSegment.toFormula());

    }
    @Test (expected = IllegalArgumentException.class)
    public void cannotFormatToFormulaWithZeroArgumentA(){
        LineSegment2D LineSegment = new LineSegment2D (0,3.1,-4.1);
        LineSegment.toFormula();
    }
    @Test (expected = IllegalArgumentException.class)
    public void cannotFormatToFormulaWithZeroArgumentB(){
        LineSegment2D LineSegment = new LineSegment2D (2.1,0,-4.1);
        LineSegment.toFormula();
    }

}
