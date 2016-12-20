package ru.unn.agile.Credit.Model;

import org.junit.Test;

import java.security.InvalidParameterException;

import static org.junit.Assert.*;

public class CreditTest {
    private static final double DELTA = 0.4;
    private final double sum = 10000;
    private final double months = 12;
    private final double percent = 20;

    @Test
    public void canCreateCredit() {
        Credit credit = new Credit(sum, months, percent);
        assertNotNull(credit);
    }

    @Test(expected = InvalidParameterException.class)
    public void canCountNegativeSum() {
        Credit credit = new Credit(-sum, months, percent);
    }

    @Test(expected = InvalidParameterException.class)
    public void canCreateNegativeMonths() {
        Credit credit = new Credit(sum, -months, percent);
    }

    @Test(expected = InvalidParameterException.class)
    public void canCreateCreditWhenWrongPercents() {
        Credit credit = new Credit(sum, months, -percent);
    }

    @Test(expected = InvalidParameterException.class)
    public void canCreateCreditWhenMonthsAreNull() {
        Credit credit = new Credit(sum, 0, percent);
    }

    @Test(expected = InvalidParameterException.class)
    public void canCreateCreditWhenParamsEmpty() {
        Credit credit = new Credit(0, 0, 0);
    }

    @Test
    public void canCountPayment() {
        Credit credit = new Credit(sum, months, percent);
        double payment = credit.countPayment();
        assertEquals(926, payment, DELTA);
    }

    @Test
    public void canCountTotalSum() {
        Credit credit = new Credit(sum, months, percent);
        double totalSum = credit.countTotalSum();
        assertEquals(11116, totalSum, DELTA);
    }

    @Test
    public void canCountOverpayment() {
        Credit credit = new Credit(sum, months, percent);
        double overpayment = credit.countOverpayment();
        assertEquals(1116, overpayment, DELTA);
    }
}
