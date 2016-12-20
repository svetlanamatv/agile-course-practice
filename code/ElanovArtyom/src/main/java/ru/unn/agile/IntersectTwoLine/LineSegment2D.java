package ru.unn.agile.IntersectTwoLine;


public class LineSegment2D {

    public LineSegment2D(final double factorA, final double factorB, final double factorC) {
        this.coefficientA = factorA;
        this.coefficientB = factorB;
        this.coefficientC = factorC;
    }

    private double coefficientA;
    private double coefficientB;
    private double coefficientC;

    public double getA() {
        return coefficientA;
    }

    public double getB() {
        return coefficientB;
    }

    public double getC() {
        return coefficientC;
    }

    public String toFormula() {

        return Formatter.getFormatted(this);
    }

    public String checkIntersection(final LineSegment2D lineSegment2) {
        double x, y;
        if (lineSegment2.getA() / getA() == lineSegment2.getB() / getB() & lineSegment2.getA() / getA() == lineSegment2.getC() / getC()) {
            return "Lines math";
            }
        else if (lineSegment2.getA() / getA() == lineSegment2.getB() / getB()) {
            return "Lines are parallel";
            }
            else {
                x = (-1) * (getC() * lineSegment2.getB() - lineSegment2.getC() * getB()) / (getA() * lineSegment2.getB() - getB() * lineSegment2.getA());
                y = (-1) * (getA() * lineSegment2.getC() - lineSegment2.getA() * getC())/(getA() * lineSegment2.getB() - lineSegment2.getA() * getB());
                return "Intersection point (" + x + ";" + y + ")";
                }
    }
}
