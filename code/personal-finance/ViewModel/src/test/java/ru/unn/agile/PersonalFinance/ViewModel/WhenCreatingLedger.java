package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class WhenCreatingLedger {
    private ViewModelObjectsMaker maker;

    @Before
    public void setUp() throws Exception {
        maker = new ViewModelObjectsMaker();
    }

    @Test
    public void andTransactionCanNotBeAddedIfAccountListIsEmpty() throws Exception {
        LedgerViewModel ledger = maker.getLedger();

        assertFalse(ledger.isAbleToAddTransaction());
    }

    @Test
    public void andTransferCanNotBeAddedIfAccountListIsEmpty() throws Exception {
        LedgerViewModel ledger = maker.getLedger();

        assertFalse(ledger.isAbleToAddTransfer());
    }

    @Test
    public void andTransferCanNotBeAddedIfAccountListHasOnlyAccount() throws Exception {
        maker.makeSavedAccount();
        LedgerViewModel ledger = maker.getLedger();

        assertFalse(ledger.isAbleToAddTransfer());
    }
}
