package ru.unn.agile.triangle.model;

public class Triangle {
    private final Point2D pointA;
    private final Point2D pointB;
    private final Point2D pointC;
    private final double lenghtAB;
    private final double lenghtBC;
    private final double lenghtAC;

    public Triangle(final Point2D pointA, final Point2D pointB, final Point2D pointC) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;

        lenghtAB = getDistanceBtwTwoPoints(pointA, pointB);
        lenghtBC = getDistanceBtwTwoPoints(pointB, pointC);
        lenghtAC = getDistanceBtwTwoPoints(pointA, pointC);
    }

    public Point2D getPointA() {
        return pointA;
    }

    public Point2D getPointB() {
        return pointB;
    }

    public Point2D getPointC() {
        return pointC;
    }

    public double getLenghtAB() {
        return lenghtAB;
    }

    public double getLenghtBC() {
        return lenghtBC;
    }

    public double getLenghtAC() {
        return lenghtAC;
    }

    public double area() {
        double p = perimeter() / 2;

        return Math.sqrt(p * (p - lenghtAB) * (p - lenghtBC) * (p - lenghtAC));
    }

    public double perimeter() {
        return lenghtAC + lenghtBC + lenghtAB;
    }

    public Circle getIncircle() {
        double x0, y0, radius;
        double perimeter = perimeter();
        double p = perimeter() / 2;

        y0 = (lenghtBC * pointA.getY()
              + lenghtAC * pointB.getY()
              + lenghtAB * pointC.getY())
              / perimeter;

        x0 = (lenghtBC * pointA.getX()
              + lenghtAC * pointB.getX()
              + lenghtAB * pointC.getX())
              / perimeter;

        radius = Math.sqrt((p - lenghtAB) * (p - lenghtBC) * (p - lenghtAC) / p);

        return new Circle(new Point2D(x0, y0), radius);
    }

    public Circle getCircumcircle() {
        double x0, y0, radius;
        final int circumradiusCoeff = 4;

        double d = 2 * (pointA.getX() * (pointB.getY() - pointC.getY())
                        + pointB.getX() * (pointC.getY() - pointA.getY())
                        + pointC.getX() * (pointA.getY() - pointB.getY()));

        double area = area();

        if (area == 0) {
            return new Circle(new Point2D(Double.NaN, Double.NaN), Double.NaN);
        }

        double aA = sumOfSquares(pointA.getX(), pointA.getY());
        double bB = sumOfSquares(pointB.getX(), pointB.getY());
        double cC = sumOfSquares(pointC.getX(), pointC.getY());

        x0 = (aA * (pointB.getY() - pointC.getY())
              + bB * (pointC.getY() - pointA.getY())
              + cC * (pointA.getY() - pointB.getY())) / d;

        y0 = (aA * (pointC.getX() - pointB.getX())
              + bB * (pointA.getX() - pointC.getX())
              + cC * (pointB.getX() - pointA.getX())) / d;

        radius = lenghtAB * lenghtAC * lenghtBC / (circumradiusCoeff * area);

        return new Circle(new Point2D(x0, y0), radius);
    }

    private double sumOfSquares(final double x, final double y) {
        return Math.pow(x, 2) + Math.pow(y, 2);
    }

    private double getDistanceBtwTwoPoints(final Point2D pointOne, final Point2D pointTwo) {
        double a = Math.pow(pointOne.getX() - pointTwo.getX(), 2);
        double b = Math.pow(pointOne.getY() - pointTwo.getY(), 2);

        return Math.sqrt(a + b);
    }
}
