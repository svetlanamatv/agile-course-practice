package ru.unn.agile.IntersectTwoLine;

public final class Formatter {

    private Formatter() { }

    public static double formatPositiveDouble(final double value) {
        if (value < 0) {
            return -value;
        }
        else {
            return value;
        }
    }

    public static String getFormatted(final LineSegment2D lineSegment) {
        StringBuffer buffer = new StringBuffer();
        if (lineSegment.getA() == 0.0 || lineSegment.getB() == 0.0) {
            throw new IllegalArgumentException("arguments A and B can not be null");
        }
            else {
            double A = formatPositiveDouble(lineSegment.getA());
            buffer.append(lineSegment.getA() < 0 ? "-" : "");
            buffer.append(A + "*x");
            double B = formatPositiveDouble(lineSegment.getB());
            buffer.append(lineSegment.getB() < 0 ? "-" : "+");
            buffer.append(B + "*y");
            double C = formatPositiveDouble(lineSegment.getC());
            buffer.append(lineSegment.getC() < 0 ? "-" : "+");
            buffer.append(C);
            buffer.append("=0");
            return buffer.toString();
        }
    }
}
