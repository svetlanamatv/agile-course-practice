package ru.unn.agile.Integration.Model;

/**
 * Created by Annet on 09.01.2017.
 */
public class IntegrationMethod {

    public int n;
    public double a;
    public double b;

    public IntegrationMethod(int n, double a, double b) {
        this.n = n;
        this.a = a;
        this.b = b;
    }

    public double Rectangle(int functionType) {

        Function func = new Function(functionType);

        double I = 0, h = (b - a) / n;

        for (double i = 0; i < n; i++) {
            I += (func.getResult((2 * a + 2 * i * h + h) / 2));
        }
        I *= h;
        return I;
    }

    public double Trapezoid(int functionType) {

        Function func = new Function(functionType);

        double I = 0, h = (b - a) / n;

        for (double i = 1; i < n; i++) {
            I += func.getResult(i * h);
        }
        I += (func.getResult(a) + func.getResult(b)) / 2;
        I *= h;

        return I;
    }

    public double Simpson(int functionType) {

        Function func = new Function(functionType);

        double I = 0;
        n = n / 2;

        for (double i = 1; i < 2 * n; i += 2) {
            I += 4 * func.getResult(a + ((b - a) * i / (2 * n)));
        }
        for (double i = 2; i < 2 * n - 1; i += 2) {
            I += 2 * func.getResult(a + ((b - a) * i / (2 * n)));
        }

        I += func.getResult(a) + func.getResult(b);
        I *= (b - a) / (6 * n);

        return I;
    }
}
