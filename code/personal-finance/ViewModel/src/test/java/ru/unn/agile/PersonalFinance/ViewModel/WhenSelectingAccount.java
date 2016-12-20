package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WhenSelectingAccount {
    private AccountViewModel account;
    private LedgerViewModel ledger;

    @Before
    public void setUp() throws Exception {
        ViewModelObjectsMaker maker = new ViewModelObjectsMaker();
        ledger = maker.getLedger();
        account = maker.makeSavedAccount();
    }

    @Test
    public void andCanAddTransactionPropertyChangesToTrue() throws Exception {
        ledger.setSelectedAccount(account);

        boolean canAddTransaction = ledger.isAbleToAddTransaction();
        assertTrue(canAddTransaction);
    }
}
