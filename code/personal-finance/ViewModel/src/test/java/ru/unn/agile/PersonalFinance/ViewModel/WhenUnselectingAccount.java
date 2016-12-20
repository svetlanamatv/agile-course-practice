package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class WhenUnselectingAccount {
    private LedgerViewModel ledger;

    @Before
    public void setUp() throws Exception {
        ViewModelObjectsMaker maker = new ViewModelObjectsMaker();
        ledger = maker.getLedger();
        AccountViewModel account = maker.makeSavedAccount();
        ledger.setSelectedAccount(account);
    }

    @Test
    public void andCanAddTransactionPropertyChangesToFalse() throws Exception {
        ledger.setSelectedAccount(null);

        boolean canAddTransaction = ledger.isAbleToAddTransaction();
        assertFalse(canAddTransaction);
    }
}
