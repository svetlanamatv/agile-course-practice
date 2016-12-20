package ru.unn.agile.IntersectTwoLine;

/**
 * Created by User on 020 20.12.16.
 */
public final class Formatter {

    public static double formatPositiveDouble(final double value) {
        if (value <0)
            return -value;
        else
            return value;
    }

    public static String getFormatted(final LineSegment2D Line) {
        StringBuffer buffer = new StringBuffer();
        if (Line.getA()==0.0 || Line.getB()==0.0)
        throw new IllegalArgumentException("arguments A and B can not be null");
            else{
            double A = formatPositiveDouble(Line.getA());
            buffer.append(Line.getA() < 0 ? "-" : "");
            buffer.append(A + "*x");
            double B = formatPositiveDouble(Line.getB());
            buffer.append(Line.getB() < 0 ? "-" : "+");
            buffer.append(B + "*y");
            double C = formatPositiveDouble(Line.getC());
            buffer.append(Line.getC() < 0 ? "-" : "+");
            buffer.append(C);
            buffer.append("=0");
            return buffer.toString();
        }
    }
}
