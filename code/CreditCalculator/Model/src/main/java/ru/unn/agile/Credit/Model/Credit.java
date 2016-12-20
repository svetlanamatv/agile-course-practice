package ru.unn.agile.Credit.Model;

import java.security.InvalidParameterException;

public class Credit {
    private final double sum;
    private final double months;
    private final double percent;
    private static final double PERCENT_MAX_VALUE = 100;
    private static final double MONTHS_IN_THE_YEAR = 12;

    public Credit(final double sum, final double months, final double percent) {
        if (sum <= 0) {
            throw new InvalidParameterException("Wrong input data!");
        }
        if (months <= 0) {
            throw new InvalidParameterException("Wrong input data!");
        }
        if (percent <= 0 || percent > PERCENT_MAX_VALUE) {
            throw new InvalidParameterException("Wrong input data!");
        }
        this.sum = sum;
        this.months = months;
        this.percent = percent;
    }

    public double countPayment() {
        double normalPercent =
                (percent / PERCENT_MAX_VALUE) / MONTHS_IN_THE_YEAR;
        double fractionCountPay = normalPercent
                / (Math.pow(1 + normalPercent, months) - 1);
        return sum * (normalPercent + fractionCountPay);
    }

    public double countTotalSum() {
        double payment = countPayment();
        return payment * months;
    }

    public double countOverpayment() {
        double totalSum = countTotalSum();
        return totalSum - sum;
    }
}
