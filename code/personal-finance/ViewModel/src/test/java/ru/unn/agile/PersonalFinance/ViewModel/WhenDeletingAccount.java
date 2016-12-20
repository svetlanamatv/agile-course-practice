package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static ru.unn.agile.PersonalFinance.ViewModel.AssertHelper.assertNotContains;

public class WhenDeletingAccount {
    private LedgerViewModel ledger;
    private AccountViewModel account;

    @Before
    public void setUp() throws Exception {
        ViewModelObjectsMaker maker = new ViewModelObjectsMaker();
        ledger = maker.getLedger();
        account = maker.makeSavedAccount();
    }

    @Test
    public void andItIsDeletedFromAccountList() throws Exception {
        account.delete();

        assertNotContains(ledger.getAccounts(), account);
    }

    @Test
    public void andItIsMarkedAsDeleted() throws Exception {
        account.delete();

        assertTrue(account.isDeleted());
    }
}
