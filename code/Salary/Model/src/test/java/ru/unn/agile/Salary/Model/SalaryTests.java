package ru.unn.agile.Salary.Model;

import org.junit.Test;
import java.security.InvalidParameterException;
import static org.junit.Assert.*;

public class SalaryTests {
    private final double baseSalary = 10000;
    private final int hoursPerWeek = 40;
    private final int extraHours = 10;
    private final int leaveDays = 2;

    @Test
    public void testToCreateSalary() {
        Salary salary = new Salary(baseSalary, hoursPerWeek, extraHours, leaveDays);
        assertNotNull(salary);
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateNegativeBaseSalary() {
        Salary salary = new Salary(-baseSalary, hoursPerWeek, extraHours, leaveDays);
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateNullBaseSalary() {
        Salary salary = new Salary(0, hoursPerWeek, extraHours, leaveDays);
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateNegativeHoursPerWeek() {
        Salary salary = new Salary(baseSalary, -hoursPerWeek, extraHours, leaveDays);
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateZeroHoursPerWeek() {
        Salary salary = new Salary(baseSalary, 0, extraHours, leaveDays);
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateNegativeExtraHours() {
        Salary salary = new Salary(baseSalary, hoursPerWeek, -extraHours, leaveDays);
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateNegativeLeaveDays() {
        Salary salary = new Salary(baseSalary, hoursPerWeek, extraHours, -leaveDays);
    }

    @Test(expected = InvalidParameterException.class)
    public void testCreateWrongLeaveDays() {
        Salary salary = new Salary(baseSalary, hoursPerWeek, extraHours, 30);
    }

    @Test
    public void testCountHourlyWage() {
        Salary salary = new Salary(baseSalary, hoursPerWeek, extraHours, leaveDays);
        double hourlyWage = salary.countHourlyWage();
        assertEquals(62.5, hourlyWage, 0);
    }

    @Test
    public void testCountDailyWage() {
        Salary salary = new Salary(baseSalary, hoursPerWeek, extraHours, leaveDays);
        double dailyWage = salary.countDailyWage();
        assertEquals(500, dailyWage, 0);
    }

    @Test
    public void testCountSalary() {
        Salary salary = new Salary(baseSalary, hoursPerWeek, extraHours, leaveDays);
        double outputSalary = salary.countSalary();
        assertEquals(9625, outputSalary, 0);
    }
}
