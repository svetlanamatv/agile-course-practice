package ru.unn.agile.NewtonRoots.Model;

import org.junit.Test;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

public class MathFunctionTests {

    private static final double EPSILON = 1e-15;

    @Test(expected = Exception.class)
    public void createFunctionFromInvalidExpression() throws Exception {
        MathFunction func = new MathFunction("-z");
    }

    @Test
    public void evaluateValidFunction() {
        try  {
            MathFunction func = new MathFunction("x^2");
            double x = 1.5;

            assertTrue(Math.abs(func.compute(x) - x * x) < EPSILON);
        } catch (Exception e)  {
            fail(e.getMessage());
        }
    }
}
