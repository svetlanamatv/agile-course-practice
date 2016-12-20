package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WhenEditingAccount {
    private static final String INITIAL_ACCOUNT_NAME = "Initial account name";
    private static final String CHANGED_ACCOUNT_NAME = "Changed account name";

    private AccountViewModel account;

    @Before
    public void setUp() throws Exception {
        ViewModelObjectsMaker maker = new ViewModelObjectsMaker();
        account = maker.makeSavedAccount(INITIAL_ACCOUNT_NAME);
        account.startChanging();
    }

    @Test
    public void andModelAccountNameChangedIfItWasSaved() throws Exception {
        account.setName(CHANGED_ACCOUNT_NAME);
        account.save();

        String modelAccountName = account.getModelAccount().getName();
        assertEquals(modelAccountName, CHANGED_ACCOUNT_NAME);
    }

    @Test
    public void andItsNameRecoversIfChangesWereNotApplied() throws Exception {
        account.setName(CHANGED_ACCOUNT_NAME);
        account.revertChanges();

        assertEquals(account.getName(), INITIAL_ACCOUNT_NAME);
    }

    @Test
    public void andModelAccountNameRecoversIfChangesWereNotApplied() throws Exception {
        account.setName(CHANGED_ACCOUNT_NAME);
        account.revertChanges();

        String modelAccountName = account.getModelAccount().getName();
        assertEquals(modelAccountName, INITIAL_ACCOUNT_NAME);
    }
}
