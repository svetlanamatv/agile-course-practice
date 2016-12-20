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

    public static String getFormatted(final LineSegment2D LineSegment) {
        StringBuffer buffer = new StringBuffer();
        if (LineSegment.getA()==0.0 || LineSegment.getB()==0.0) {
            throw new IllegalArgumentException("arguments A and B can not be null");
        }
            else {
            double A = formatPositiveDouble(LineSegment.getA());
            buffer.append(LineSegment.getA() < 0 ? "-" : "");
            buffer.append(A + "*x");
            double B = formatPositiveDouble(LineSegment.getB());
            buffer.append(LineSegment.getB() < 0 ? "-" : "+");
            buffer.append(B + "*y");
            double C = formatPositiveDouble(LineSegment.getC());
            buffer.append(LineSegment.getC() < 0 ? "-" : "+");
            buffer.append(C);
            buffer.append("=0");
            return buffer.toString();
        }
    }
}
