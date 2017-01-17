package ru.unn.agile.Salary.Model;

import java.security.InvalidParameterException;

public class Salary {
    private final double baseSalary;
    private final int hoursPerWeek;
    private final int extraHours;
    private final int leaveDays;

    private static final int DAYS_IN_WEEK = 5;
    private static final int WEEKS_IN_MONTH = 4;

    public Salary(final double baseSalary, final int hoursPerWeek,
        final int extraHours, final int leaveDays) {
        if (baseSalary <= 0) {
            throw new InvalidParameterException("Not positive base salary");
        }

        if (hoursPerWeek <= 0) {
            throw new InvalidParameterException("Wrong hours per week");
        }

        if (extraHours < 0) {
            throw new InvalidParameterException("Negative extra hours");
        }

        if (leaveDays < 0) {
            throw new InvalidParameterException("Negative leave days");
        }

        if (leaveDays > DAYS_IN_WEEK * WEEKS_IN_MONTH) {
            throw new InvalidParameterException("Number of leave days is bigger than working days");
        }

        this.baseSalary = baseSalary;
        this.hoursPerWeek = hoursPerWeek;
        this.extraHours = extraHours;
        this.leaveDays = leaveDays;
    }

    public double countHourlyWage() {
        return this.baseSalary / (this.hoursPerWeek * WEEKS_IN_MONTH);
    }

    public double countDailyWage() {
        return countHourlyWage() * (this.hoursPerWeek / DAYS_IN_WEEK);
    }

    public double countSalary() {
        return baseSalary + countHourlyWage() * this.extraHours
        - countDailyWage() * this.leaveDays;
    }
}
