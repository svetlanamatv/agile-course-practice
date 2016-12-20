package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static ru.unn.agile.PersonalFinance.ViewModel.AssertHelper.assertContains;

public class WhenSavingExternalTransaction {
    private ViewModelObjectsMaker maker;
    private AccountViewModel account;

    @Before
    public void setUp() throws Exception {
        maker = new ViewModelObjectsMaker();
        account = maker.makeSavedAccount();
    }

    @Test
    public void andItIsAddedToTransactionsList() throws Exception {
        TransactionViewModel transaction = maker.makeExternalTransaction(account);

        transaction.save();

        assertContains(account.getTransactions(), transaction);
    }

    @Test
    public void andAccountBalanceChanges() throws Exception {
        TransactionViewModel transaction = maker.makeExternalTransaction(account);
        transaction.setAmount(10);
        int balanceBeforeTransaction = account.getBalance();

        transaction.save();

        assertNotEquals(account.getBalance(), balanceBeforeTransaction);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void andIfAccountWasNotSavedItCauseFail() throws Exception {
        AccountViewModel unsavedAccount = maker.makeAccount();
        TransactionViewModel transaction = maker.makeExternalTransaction(unsavedAccount);

        transaction.save();
    }
}
