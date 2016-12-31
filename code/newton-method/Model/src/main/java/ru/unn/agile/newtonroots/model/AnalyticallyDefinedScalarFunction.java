package ru.unn.agile.newtonroots.model;

import org.nfunk.jep.JEP;

public class AnalyticallyDefinedScalarFunction implements ScalarFunction {
    private final JEP parser;
    public AnalyticallyDefinedScalarFunction(final String expression)
            throws InvalidFunctionExpressionException {
        parser = new JEP();
        parser.addStandardFunctions();
        parser.addStandardConstants();
        parser.addVariable("x", 0);
        parser.parseExpression(expression);
        if (parser.hasError())  {
            throw new InvalidFunctionExpressionException(parser.getErrorInfo());
        }
    }
    @Override
    public double compute(final double x)  {
        parser.addVariable("x", x);
        return parser.getValue();
    }

    public class InvalidFunctionExpressionException extends RuntimeException {
        public InvalidFunctionExpressionException(final String message) {
            super(message);
        }
    }
}
