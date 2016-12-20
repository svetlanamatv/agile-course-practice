package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class WhenCreatingAccount {
    private static final String EXISTING_ACCOUNT_NAME = "Debit card";
    private static final String ANOTHER_ACCOUNT_NAME = "Yet another debit card";

    private ViewModelObjectsMaker maker;

    @Before
    public void setUp() throws Exception {
        maker = new ViewModelObjectsMaker();
        maker.makeSavedAccount(EXISTING_ACCOUNT_NAME);
    }

    @Test(expected = NullPointerException.class)
    public void andIfLedgerModelIsNullItCausesFail() throws Exception {
        new AccountViewModel(null);
    }

    @Test
    public void andItCanNotBeSavedIfAccountWithSameNameAlreadyExists() throws Exception {
        AccountViewModel accountWithExistingName =
                maker.makeAccount(EXISTING_ACCOUNT_NAME);

        assertFalse(accountWithExistingName.isAbleToSave());
    }

    @Test
    public void andItCanBeSavedIfAccountWithSameNameDoesNotExist() throws Exception {
        AccountViewModel accountWithNewName =
                maker.makeAccount(ANOTHER_ACCOUNT_NAME);

        assertTrue(accountWithNewName.isAbleToSave());
    }
}
