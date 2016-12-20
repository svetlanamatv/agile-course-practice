package ru.unn.agile.PersonalFinance.Model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class WhenAddingTransactionToAccount {
    private Account cash = new Account(75, "Cash");
    private Account debitCard = new Account(150, "Debit card");
    private GregorianCalendar date = new GregorianCalendar(2015, Calendar.MAY, 9);

    @Test
    public void andItIsExpenseBalanceShouldDecrease() {
        cash.addExternalTransaction(ExternalTransaction.expenseBuilder(25).build());

        assertEquals(50, cash.getBalance());
    }

    @Test
    public void andItIsIncomeBalanceShouldIncrease() {
        cash.addExternalTransaction(ExternalTransaction.incomeBuilder(50).build());

        assertEquals(125, cash.getBalance());
    }

    @Test
    public void andItIsTransferAccountsBalancesAdjust() {
        debitCard.transferTo(cash, 50);

        assertEquals(125, cash.getBalance());
        assertEquals(100, debitCard.getBalance());
    }

    @Test
    public void andItIsExpenseItIsSavedToTransactionList() {
        cash.addExternalTransaction(ExternalTransaction.expenseBuilder(25).build());
        List<Transaction> transactions = cash.getTransactions();
        Transaction last = transactions.get(transactions.size() - 1);

        assertEquals(-25, last.getAmount());
    }

    @Test
    public void andItIsIncomeItIsSavedToTransactionList() {
        debitCard.addExternalTransaction(ExternalTransaction.incomeBuilder(50).build());
        List<Transaction> transactions = debitCard.getTransactions();
        Transaction last = transactions.get(transactions.size() - 1);

        assertEquals(50, last.getAmount());
    }

    @Test
    public void andItIsTransferItIsSavedToBothAccountsLists() {
        debitCard.transferTo(cash, 50);
        Transaction cashLast = cash.getTransactions().get(
                cash.getTransactions().size() - 1);
        Transaction debitCardLast = debitCard.getTransactions().get(
                debitCard.getTransactions().size() - 1);

        assertEquals(50, cashLast.getAmount());
        assertEquals(50, debitCardLast.getAmount());
    }

    @Test
    public void andItIsExpenseDescriptionIsSaved() {
        cash.addExternalTransaction(ExternalTransaction.expenseBuilder(25)
                .description("Candy")
                .build());
        Transaction lastExpense = cash.getTransactions().get(
                cash.getTransactions().size() - 1);

        assertEquals("Candy", ((ExternalTransaction) lastExpense).getDescription());
    }

    @Test
    public void andItIsIncomeDescriptionIsSaved() {
        debitCard.addExternalTransaction(ExternalTransaction.incomeBuilder(50)
                .description("Salary")
                .build());
        Transaction lastIncome = debitCard.getTransactions().get(
                debitCard.getTransactions().size() - 1);

        assertEquals("Salary", ((ExternalTransaction) lastIncome).getDescription());
    }

    @Test
    public void andItIsExpenseCategoryIsSaved() {
        Category food = new Category("Food");
        cash.addExternalTransaction(ExternalTransaction.expenseBuilder(25)
                .category(food)
                .build());
        Transaction lastExpense = cash.getTransactions().get(0);

        assertEquals(food, ((ExternalTransaction) lastExpense).getCategory());
    }

    @Test
    public void andItIsIncomeCategoryIsSaved() {
        Category taxReturn = new Category("Tax return");
        cash.addExternalTransaction(ExternalTransaction.incomeBuilder(50)
                .category(taxReturn)
                .build());
        Transaction lastIncome = cash.getTransactions().get(0);

        assertEquals(taxReturn, ((ExternalTransaction) lastIncome).getCategory());
    }

    @Test(expected = IllegalArgumentException.class)
    public void andItIsTransferNegativeAmountShouldCauseFailure() {
        debitCard.transferTo(cash, -20);
    }

    @Test
    public void andItIsExpenseDateIsSaved() {
        cash.addExternalTransaction(ExternalTransaction.expenseBuilder(25).date(date).build());
        ExternalTransaction lastExpense = (ExternalTransaction) cash.getTransactions().get(0);

        assertEquals(date, lastExpense.getDate());
    }

    @Test
    public void andItIsIncomeDateIsSaved() {
        cash.addExternalTransaction(ExternalTransaction.incomeBuilder(25).date(date).build());
        ExternalTransaction lastIncome = (ExternalTransaction) cash.getTransactions().get(0);

        assertEquals(date, lastIncome.getDate());
    }

    @Test
    public void andItIsTransferDateIsSaved() {
        debitCard.transferTo(cash, 25, date);
        Transfer lastTransfer = (Transfer) cash.getTransactions().get(0);

        assertEquals(date, lastTransfer.getDate());
    }

    @Test
    public void andItIsExternalCounterPartyIsSaved() {
        cash.addExternalTransaction(ExternalTransaction.expenseBuilder(25)
                .description("M&M's")
                .counterparty("Auchan")
                .build());
        ExternalTransaction lastExpense = (ExternalTransaction) cash.getTransactions().get(0);

        assertEquals("Auchan", lastExpense.getCounterparty());
    }
}
