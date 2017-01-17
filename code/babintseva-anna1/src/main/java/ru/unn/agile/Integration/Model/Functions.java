package ru.unn.agile.Integration.Model;

public class Functions {
    private int number;

    public Functions(int number) {
        this.number = number;
    }
    
    public double getResult(double x){
        switch (number){
            case 1: {
                return F1(x);
            }
            case 2:{
                return F2(x);
            }
            case 3:{
                return F3(x);
            }
            default:throw new IllegalArgumentException("Incorrect function number");
        }
    }

    private double F1(double x) {
        return 1 / (1 + x * x);
    }

    private double F2(double x) {
        return 1 / (1 + x * x) + Math.cos(10 * x);
    }

    private double F3(double x) {
        return 1 / (1 + x * x) + Math.cos(100 * x);
    }
}
