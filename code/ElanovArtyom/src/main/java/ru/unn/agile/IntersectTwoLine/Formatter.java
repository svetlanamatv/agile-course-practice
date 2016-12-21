package ru.unn.agile.IntersectTwoLine;

public final class Formatter {

    private Formatter() { }

    public static double formatPositiveDouble(final double value) {
        if (value < 0) {
            return -value;}
        else {
            return value;
        }
    }

    public static String getFormatted(final LineSegment2D lineSegment) {
        StringBuffer buffer = new StringBuffer();
        if (lineSegment.getA() == 0.0 || lineSegment.getB() == 0.0) {
            throw new IllegalArgumentException("arguments A and B can not be null");}
            else {
            double coefficientA = formatPositiveDouble(lineSegment.getA());
            buffer.append(lineSegment.getA() < 0 ? "-" : "");
            buffer.append(coefficientA + "*x");
            double coefficientB = formatPositiveDouble(lineSegment.getB());
            buffer.append(lineSegment.getB() < 0 ? "-" : "+");
            buffer.append(coefficientB + "*y");
            double coefficientC = formatPositiveDouble(lineSegment.getC());
            buffer.append(lineSegment.getC() < 0 ? "-" : "+");
            buffer.append(coefficientC);
            buffer.append("=0");
            return buffer.toString();
        }
    }
}
