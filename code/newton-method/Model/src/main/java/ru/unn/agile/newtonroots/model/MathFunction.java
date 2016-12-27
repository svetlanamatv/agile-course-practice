package ru.unn.agile.newtonroots.model;

import org.nfunk.jep.JEP;

public class MathFunction implements FunctionInterface  {
    private final JEP parser;
    public MathFunction(final String expression) throws Exception {
        parser = new JEP();
        parser.addStandardFunctions();
        parser.addStandardConstants();
        parser.addVariable("x", 0);
        parser.parseExpression(expression);
        if (parser.hasError())  {
            throw new Exception(parser.getErrorInfo());
        }
    }
    @Override
    public double compute(final double x)  {
        parser.addVariable("x", x);
        return parser.getValue();
    }
}
