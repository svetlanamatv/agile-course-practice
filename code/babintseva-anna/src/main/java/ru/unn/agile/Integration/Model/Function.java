package ru.unn.agile.Integration.Model;

/**
 * Created by Annet on 09.01.2017.
 */
public class Function {
    
    private int number;

    public Function(int number) {
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
            default:{
                return 0;
            }
        }
    }
    // Функция 1/(1+x^2)
    private double F1(double x)
    {
        return 1 / (1 + x * x);
    }

    // Функция 1/(1+x^2) + cos(10x)
    private double F2(double x)
    {
        return 1 / (1 + x * x) + Math.cos(10*x);
    }

    // Функция 1/(1+x^2) + cos(100x)
    private double F3(double x)
    {
        return 1 / (1 + x * x) + Math.cos(100 * x);
    }
}
