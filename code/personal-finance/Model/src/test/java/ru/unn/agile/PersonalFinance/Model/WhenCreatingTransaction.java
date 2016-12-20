package ru.unn.agile.PersonalFinance.Model;

import org.junit.Test;

public class WhenCreatingTransaction {
    @Test(expected = IllegalArgumentException.class)
    public void andItIsExpenseNegativeAmountShouldCauseFailure() {
        ExternalTransaction.expenseBuilder(-5).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void andItIsIncomeNegativeAmountShouldCauseFailure() {
        ExternalTransaction.incomeBuilder(-5).build();
    }
}
