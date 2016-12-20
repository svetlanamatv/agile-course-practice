package ru.unn.agile.SquareCalculator.Model;


public class SquareCalculator {
    public static final  int SPHERE_CONST = 4;
    public  static final int CUBE_CONST = 6;
    public static final int CYLINDER_CONST = 2;
    public static final int PARALLELEPIPED_CONST = 2;

    public double getSphereSquare(final double radius) {

        checkParams("Radius must not be negative!", radius);

        return SPHERE_CONST * Math.PI * radius * radius;

    }

    public double getConeSquare(final double radius, final double rulingofcone) {
        checkParams("Radius and Ruling Of Cone must not be negative!", radius, rulingofcone);
        return Math.PI * radius * (radius + rulingofcone);
    }

    public double getCubeSquare(final double lenght) {
        checkParams("Lenght must not be negative!", lenght);
        return CUBE_CONST * lenght * lenght;
    }



    public double getCylinderSquare(final double radius, final double height) {
        checkParams("Radius and height must not be negative!", radius, height);
        return CYLINDER_CONST * Math.PI * radius * (radius + height);
    }

    // for parallelepiped square
    public double getParallelSquare(final double height, final double lenght, final double width) {
        checkParams("height , lenght and width must not be negative!", height, lenght, width);
        return PARALLELEPIPED_CONST * (height * lenght + height * width + lenght * width);
    }


    private void checkParams(final String message, final double... params) {
        for (double param : params) {
            if (param < 0) {
                throw new IllegalArgumentException(message);
            }
        }
    }
}

