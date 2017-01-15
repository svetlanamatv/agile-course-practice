package ru.unn.agile.Integration.Model;

public class IntegrationMethods {
    private int N;
    private double A;
    private double B;

    public IntegrationMethods(int n, double a, double b) {
        N = n;
        A = a;
        B = b;
    }

    public double Rectangle(int functionType) {
        Functions func = new Functions(functionType);
        double res = 0, h = (B - A) / N;

        for (int i = 0; i < N; i++) {
            res += (func.getResult((2 * A + 2 * i * h + h) / 2));
        }
        res *= h;
        return res;
    }

    public double Trapezoid(int functionType) {
        Functions func = new Functions(functionType);
        double res = 0, h = (B - A) / N;

        for (int i = 1; i < N; i++) {
            res += func.getResult(i * h);
        }
        res += (func.getResult(A) + func.getResult(B)) / 2;
        res *= h;

        return res;
    }

    public double Simpson(int functionType) {
        Functions func = new Functions(functionType);

        double res = 0;
        int n = N / 2;

        for (int i = 1; i < 2 * n; i += 2) {
            res += 4 * func.getResult(A + ((B - A) * i / (2 * n)));
        }
        for (int i = 2; i < 2 * n - 1; i += 2) {
            res += 2 * func.getResult(A + ((B - A) * i / (2 * n)));
        }

        res += func.getResult(A) + func.getResult(B);
        res *= (B - A) / (6 * n);

        return res;
    }
}
