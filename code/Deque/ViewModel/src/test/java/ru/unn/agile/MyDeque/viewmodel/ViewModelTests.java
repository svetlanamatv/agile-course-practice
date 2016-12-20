package ru.unn.agile.MyDeque.viewmodel;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static ru.unn.agile.MyDeque.viewmodel.ViewModel.Status.*;
import static ru.unn.agile.MyDeque.viewmodel.ViewModel.Operations.*;

import static org.junit.Assert.*;

public class ViewModelTests {

    private ViewModel viewmodel;

    @Before
    public void setUp() {
        viewmodel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewmodel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewmodel.getValue());
        assertEquals(WAITING, viewmodel.getStatus());
        assertEquals("", viewmodel.getResult());
    }

    @Test
    public void isWaitingStatusInTheBeginning() {
        assertEquals(WAITING, viewmodel.getStatus());
    }

    @Test
    public void canReportBadFormat() {
        viewmodel.setValue("qwe");
        viewmodel.textFieldKey(KeyboardsKeys.ANY);

        assertEquals(BAD_FORMAT, viewmodel.getStatus());
    }

    @Test
    public void canCleanStatusIfAcceptIsOK() {
        viewmodel.setValue("qwe");
        viewmodel.textFieldKey(KeyboardsKeys.ANY);
        viewmodel.setValue("3");
        viewmodel.textFieldKey(KeyboardsKeys.ANY);

        assertEquals(READY, viewmodel.getStatus());
    }

    @Test
    public void isAcceptButtonDisabledInitially() {
        assertEquals(false, viewmodel.isAcceptButtonEnabled());
    }

    @Test
    public void isAcceptButtonDisabledWhenFormatIsBad() {
        inputField();
        viewmodel.setValue("trash");
        viewmodel.textFieldKey(KeyboardsKeys.ANY);

        assertEquals(false, viewmodel.isAcceptButtonEnabled());
    }

    private void inputField() {
        viewmodel.setValue("5");
    }

    @Test
    public void canGetOperationName() {
        String addName = PUSH_HEAD.toString();
        assertEquals("Push head", addName);
    }

    @Test
    public void canGetQuantityOfOperations() {
        int quantityOfOperations = values().length;
        assertEquals(4, quantityOfOperations);
    }

    @Test
    public void canCompareOperationsByName() {
        assertNotEquals(POP_HEAD, POP_TAIL);
    }

    @Test
    public void canSetPushHeadOperation() {
        viewmodel.setOperation(PUSH_HEAD);
        assertEquals(PUSH_HEAD, viewmodel.getOperation());
    }

    @Test
    public void canSetPushTailOperation() {
        viewmodel.setOperation(PUSH_TAIL);
        assertEquals(PUSH_TAIL, viewmodel.getOperation());
    }

    @Test
    public void canSetPopHeadOperation() {
        viewmodel.setOperation(POP_HEAD);
        assertEquals(POP_HEAD, viewmodel.getOperation());
    }

    @Test
    public void canSetPopTailOperation() {
        viewmodel.setOperation(POP_TAIL);
        assertEquals(POP_TAIL, viewmodel.getOperation());
    }

    @Test
    public void canSetSuccess() {
        inputField();

        viewmodel.accept();

        assertEquals(SUCCESS, viewmodel.getStatus());
    }

    @Test
    public void canPerformPushTailAction() {
        viewmodel.setValue("6");
        viewmodel.setOperation(PUSH_TAIL);

        viewmodel.accept();

        assertEquals("6", viewmodel.getResult());
    }

    @Test
    public void canPerformPushHeadAction() {
        viewmodel.setValue("7");
        viewmodel.setOperation(PUSH_HEAD);

        viewmodel.accept();

        assertEquals("7", viewmodel.getResult());
    }

    @Test
    public void canPerformPushToHeadAndPopFromHead() {
        viewmodel.setOperation(PUSH_HEAD);
        for (int i = 4; i < 6; i++) {
            viewmodel.setValue(Integer.toString(i));
            viewmodel.accept();
        }

        viewmodel.setOperation(POP_HEAD);

        viewmodel.accept();

        assertEquals("5", viewmodel.getResult());
    }

    @Test
    public void canPerformPushToHeadAndPopFromTail() {
        viewmodel.setOperation(PUSH_HEAD);
        for (int i = 7; i < 9; i++) {
            viewmodel.setValue(Integer.toString(i));
            viewmodel.accept();
        }

        viewmodel.setOperation(POP_TAIL);

        viewmodel.accept();

        assertEquals("7", viewmodel.getResult());
    }

}
