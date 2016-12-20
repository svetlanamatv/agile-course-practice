package ru.unn.agile.IntersectTwoLine;


public class LineSegment2D {

    public LineSegment2D (final double factor_A, final double factor_B, final double factor_C) {
        this.A=factor_A;
        this.B=factor_B;
        this.C=factor_C;
    }

    private double A;
    private double B;
    private double C;

    public double getA() {
        return A;
    }

    public double getB() {
        return B;
    }

    public double getC() {
        return C;
    }

    public String toFormula() {

        return Formatter.getFormatted(this);
    }

    public String CheckIntersection(final LineSegment2D LineSegment2) {
        double x,y;
        if (LineSegment2.getA()/getA()==LineSegment2.getB()/getB()&LineSegment2.getA()/getA()==LineSegment2.getC()/getC())
            return "Lines math";
        else if (LineSegment2.getA()/getA()==LineSegment2.getB()/getB())
            return "Lines are parallel";
            else {
                x = (-1)*(getC()*LineSegment2.getB()-LineSegment2.getC()*getB())/(getA()*LineSegment2.getB()-getB()*LineSegment2.getA());
                y = (-1)*(getA()*LineSegment2.getC()-LineSegment2.getA()*getC())/(getA()*LineSegment2.getB()-LineSegment2.getA()*getB());
                return "Intersection point (" + x + ";" + y +")";
                }
    }
}
