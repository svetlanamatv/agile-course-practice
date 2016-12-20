package ru.unn.agile.PersonalFinance.Model;

import java.util.GregorianCalendar;

public class ExternalTransaction extends Transaction {
    private final String description;
    private final String counterparty;
    private Category category;

    public ExternalTransaction(
            final int amount,
            final String description,
            final Category category,
            final GregorianCalendar date,
            final String counterparty) {

        super(amount, date);
        this.description = description;
        this.category = category;
        this.counterparty = counterparty;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean isExternal() {
        return true;
    }

    @Override
    public boolean isTransfer() {
        return false;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public String getCounterparty() {
        return counterparty;
    }


    public static Builder expenseBuilder(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Consider using opposite transaction type");
        }

        return new Builder(-amount);
    }

    public static Builder incomeBuilder(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Consider using opposite transaction type");
        }

        return new Builder(amount);
    }


    public static class Builder {
        private final int transactionAmount;
        private String transactionDescription;
        private Category transactionCategory;
        private GregorianCalendar transactionDate;
        private String transactionCounterparty;

        public Builder(final int amount) {
            this.transactionAmount = amount;
        }

        public Builder description(final String description) {
            this.transactionDescription = description;
            return this;
        }

        public Builder category(final Category category) {
            this.transactionCategory = category;
            return this;
        }

        public Builder date(final GregorianCalendar date) {
            this.transactionDate = date;
            return this;
        }

        public Builder counterparty(final String counterparty) {
            this.transactionCounterparty = counterparty;
            return this;
        }

        public ExternalTransaction build() {
            return new ExternalTransaction(
                    transactionAmount,
                    transactionDescription,
                    transactionCategory,
                    transactionDate,
                    transactionCounterparty);
        }
    }
}
