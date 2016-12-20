package ru.unn.agile.PersonalFinance.Model;

import java.util.GregorianCalendar;

public abstract class Transaction implements Comparable<Transaction> {
    private final int amount;
    private final GregorianCalendar date;

    protected Transaction(final int amount, final GregorianCalendar date) {
        this.amount = amount;
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    @Override
    public int compareTo(final Transaction o) {
        if (date == null) {
            return 1;
        }

        if (o.getDate() == null) {
            return -1;
        }

        return -date.compareTo(o.getDate());
    }

    abstract boolean isExternal();
    abstract boolean isTransfer();
}
