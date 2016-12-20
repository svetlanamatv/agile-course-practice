package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static ru.unn.agile.PersonalFinance.ViewModel.AssertHelper.assertContains;

public class WhenSavingTransfer {
    private ViewModelObjectsMaker maker;
    private AccountViewModel stashAccount;
    private AccountViewModel debitCardAccount;

    @Before
    public void setUp() throws Exception {
        maker = new ViewModelObjectsMaker();
        stashAccount = maker.makeSavedAccount("Stash");
        debitCardAccount = maker.makeSavedAccount("Debit card");
    }

    @Test
    public void andItIsAddedToTransactionsList() throws Exception {
        TransferViewModel transfer = maker.makeTransfer(stashAccount, debitCardAccount);

        transfer.save();

        assertContains(stashAccount.getTransactions(), transfer);
        assertContains(debitCardAccount.getTransactions(), transfer.getLinkedTransfer());
    }

    @Test
    public void andAccountsBalanceChanges() throws Exception {
        TransferViewModel transfer = maker.makeTransfer(stashAccount, debitCardAccount);
        int transferAmount = 100;
        transfer.setAmount(transferAmount);
        int cacheBalanceBeforeTransaction = stashAccount.getBalance();
        int cardBalanceBeforeTransaction = debitCardAccount.getBalance();

        transfer.save();

        int cacheBalanceAfterTransaction = cacheBalanceBeforeTransaction - transferAmount;
        int cardBalanceAfterTransaction = cardBalanceBeforeTransaction + transferAmount;
        assertEquals(cacheBalanceAfterTransaction, stashAccount.getBalance());
        assertEquals(cardBalanceAfterTransaction, debitCardAccount.getBalance());
    }
}
