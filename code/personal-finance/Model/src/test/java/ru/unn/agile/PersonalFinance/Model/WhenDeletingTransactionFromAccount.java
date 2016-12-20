package ru.unn.agile.PersonalFinance.Model;

import org.junit.Test;
import static org.junit.Assert.*;

public class WhenDeletingTransactionFromAccount {
    private Account cash = new Account(75, "Cash");
    private Account debitCard = new Account(150, "Debit card");

    @Test
    public void balanceShouldBeUpdated() {
        cash.addExternalTransaction(ExternalTransaction.expenseBuilder(25).build());

        cash.deleteTransaction(cash.getTransactions().get(0));

        assertEquals(75, cash.getBalance());
    }

    @Test
    public void andItIsTheOnlyOneTransactionListShouldBeEmpty() {
        cash.addExternalTransaction(ExternalTransaction.expenseBuilder(25).build());

        cash.deleteTransaction(cash.getTransactions().get(0));

        assertTrue(cash.getTransactions().isEmpty());
    }

    @Test
    public void andItIsTransferBothAccountsBalancesShouldBeRestored() {
        debitCard.transferTo(cash, 50);

        debitCard.deleteTransaction(debitCard.getTransactions().get(0));

        assertEquals(75, cash.getBalance());
        assertEquals(150, debitCard.getBalance());
    }

    @Test
    public void andThereIsOnlyOneTransferInEachAccountBothAccountsListsShouldBeEmpty() {
        debitCard.transferTo(cash, 50);

        cash.deleteTransaction(cash.getTransactions().get(0));

        assertTrue(cash.getTransactions().isEmpty());
        assertTrue(debitCard.getTransactions().isEmpty());
    }
}
