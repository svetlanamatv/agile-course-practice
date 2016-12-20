package ru.unn.agile.PersonalFinance.Model;

import org.junit.Test;
import static org.junit.Assert.*;

public class WhenManagingLedger {
    private Ledger ledger = new Ledger();

    @Test
    public void ledgerIsEmptyUponCreation() {
        assertTrue(ledger.getAccounts().isEmpty());
    }

    @Test
    public void andAccountIsAddedLedgerIsNotEmptyAnymore() {
        ledger.addAccount(new Account(0, ""));

        assertFalse(ledger.getAccounts().isEmpty());
    }

    @Test
    public void andAccountIsAddedItIsLastInLedgersAccountsList() {
        Account cash = new Account(75, "Cash");

        ledger.addAccount(cash);

        assertEquals(75, ledger.getAccounts().get(0).getBalance());
        assertEquals("Cash", ledger.getAccounts().get(0).getName());
    }

    @Test
    public void andAccountIsEditedNewNameIsSaved() {
        Account cash = new Account(75, "Cash");

        cash.changeName("Paypal");

        assertEquals("Paypal", cash.getName());
    }

    @Test
    public void andAccountIsDeletedItIsNoLongerInLedger() {
        Account cash = new Account(75, "Cash");
        ledger.addAccount(cash);

        ledger.deleteAccount(cash);

        assertFalse(ledger.getAccounts().contains(cash));
    }

    @Test
    public void andAccountIsDeletedTransferInAnotherAccountIsUpdated() {
        Account cash = new Account(75, "Cash");
        Account debitCard = new Account(150, "Debit card");
        ledger.addAccount(cash);
        ledger.addAccount(debitCard);
        debitCard.transferTo(cash, 10, null);

        ledger.deleteAccount(cash);

        assertEquals(Ledger.DELETED_ACCOUNT,
                ((Transfer) debitCard.getTransactions().get(0)).getTarget());
    }

    @Test
    public void andCategoryIsAddedLedgerContainsIt() {
        Category groceries = new Category("Groceries");

        ledger.addCategory(groceries);

        assertTrue(ledger.getCategories().contains(groceries));
    }

    @Test
    public void andCategoryIsDeletedItIsNoLongerInLedger() {
        Category groceries = new Category("Groceries");
        ledger.addCategory(groceries);

        ledger.deleteCategory(groceries);

        assertFalse(ledger.getCategories().contains(groceries));
    }

    @Test
    public void andCategoryIsDeletedTransferWithThisCategoryIsUpdated() {
        Category groceries = new Category("Groceries");
        ledger.addCategory(groceries);
        Account cash = new Account(75, "Cash");
        ledger.addAccount(cash);
        cash.addExternalTransaction(ExternalTransaction.expenseBuilder(5)
                .description("Bread")
                .category(groceries)
                .build());

        ledger.deleteCategory(groceries);

        assertEquals(null, ((ExternalTransaction) cash.getTransactions().get(0)).getCategory());
    }
}
