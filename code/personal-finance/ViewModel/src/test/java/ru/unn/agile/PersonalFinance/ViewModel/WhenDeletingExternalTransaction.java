package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static ru.unn.agile.PersonalFinance.ViewModel.AssertHelper.assertNotContains;

public class WhenDeletingExternalTransaction {
    private AccountViewModel account;
    private ExternalTransactionViewModel transaction;

    @Before
    public void setUp() throws Exception {
        ViewModelObjectsMaker maker = new ViewModelObjectsMaker();
        account = maker.makeSavedAccount();
        transaction = maker.makeSavedExternalTransaction(account);
    }

    @Test
    public void andItIsDeletedFromTransactionsList() throws Exception {
        transaction.delete();

        assertNotContains(account.getTransactions(), transaction);
    }
}
