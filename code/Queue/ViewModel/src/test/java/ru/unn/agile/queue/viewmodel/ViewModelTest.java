package ru.unn.agile.queue.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.unn.agile.queue.viewmodel.ViewModel.LogMessageCliche.ADD_BUTTON_PRESSED;
import static ru.unn.agile.queue.viewmodel.ViewModel.LogMessageCliche.REMOVE_BUTTON_PRESSED;
import static ru.unn.agile.queue.viewmodel.ViewModel.LogMessageCliche.SEARCH_BUTTON_PRESSED;

public class ViewModelTest {

    private ViewModel<String> viewModel;

    public void setOuterViewModel(final ViewModel<String> viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void initBeforeTest() {
        if (viewModel == null) {
            viewModel = new ViewModel<String>(new QueueLoggerStub());
            setInitialData();
        }
    }

    @After
    public void cleanUpTest() {
        viewModel = null;
    }

    protected void setInitialData() {
        viewModel.setValue("123aaa");
        viewModel.add();
        viewModel.setValue("qwe");
        viewModel.add();
    }

    @Test
    public void addEmptyValue() throws Exception {
        viewModel.setValue("");
        viewModel.add();
        String res = "Value is empty!";
        assertEquals(res, viewModel.getResult());
    }

    @Test
    public void addCorrectValue() throws Exception {
        assertEquals(new LinkedList<>(Arrays.asList("123aaa", "qwe")), viewModel.getQueue());
    }

    @Test
    public void addCorrect2Value() throws Exception {
        viewModel.setValue("zxc");
        viewModel.add();
        assertEquals(new LinkedList<>(Arrays.asList("123aaa", "qwe", "zxc")), viewModel.getQueue());
    }

    @Test
    public void removeFromAbsentQueue() throws Exception {
        deleteValuesFromQueue();
        viewModel.setValue("qwe");
        viewModel.remove();
        assertEquals("Queue is empty! You don't delete value from empty queue!",
                viewModel.getResult());
    }

    @Test
    public void removeOfExistedValue() throws Exception {
        viewModel.setValue("123aaa");
        viewModel.remove();
        assertEquals(new LinkedList<>(Collections.singletonList("qwe")), viewModel.getQueue());
    }

    @Test
    public void removeNoExistedValue() throws Exception {
        viewModel.setValue("aaa");
        viewModel.remove();
        assertEquals("Value 'aaa' is absent in the queue!", viewModel.getResult());
    }

    @Test
    public void removeOfAbsentValue() throws Exception {
        viewModel.setValue("");
        viewModel.remove();
        assertEquals("Value is empty!", viewModel.getResult());
    }

    @Test
    public void searchIntoAbsentQueue() throws Exception {
        deleteValuesFromQueue();
        viewModel.setValue("qwe");
        viewModel.search();
        assertEquals("Queue is empty!", viewModel.getResult());
    }

    @Test
    public void searchOfExistedValue() throws Exception {
        viewModel.setValue("qwe");
        viewModel.search();
        assertEquals("The position of 'qwe' is 1", viewModel.getResult());
    }

    @Test
    public void searchNoExistedValue() throws Exception {
        viewModel.setValue("aaa");
        viewModel.search();
        assertEquals("The value 'aaa' is not found in queue",
                viewModel.getResult());
    }

    @Test
    public void searchOfAbsentValue() throws Exception {
        viewModel.setValue("");
        viewModel.search();
        assertEquals("Value is empty!", viewModel.getResult());
    }

    @Test
    public void getSize() throws Exception {
        viewModel.getSize();
        assertEquals("The size of queue is 2", viewModel.getResult());
    }

    @Test
    public void getSizeOfAbsentQueue() throws Exception {
        deleteValuesFromQueue();
        viewModel.getSize();
        assertEquals("Queue is empty!", viewModel.getResult());
    }

    @Test
    public void getSizeOfQueue() throws Exception {
        viewModel.setValue("wwq");
        viewModel.add();
        viewModel.getSize();
        assertEquals("The size of queue is 3", viewModel.getResult());
    }

    @Test
    public void getQueue() throws Exception {
        assertEquals(new LinkedList<>(Arrays.asList("123aaa", "qwe")), viewModel.getQueue());
    }

    @Test
    public void testThatAddingEmptyValueProducesCorrectLogs() {
        viewModel.setValue("");
        viewModel.add();
        String messageRegexp = ".*" + ADD_BUTTON_PRESSED + "Value is empty!.*";
        assertTrue(viewModel.getLogMessages().get(2).matches(messageRegexp));
    }

    @Test
    public void testThatAddingValidValueProducesCorrectLogs() {

        String messageRegexp = ".*" + ADD_BUTTON_PRESSED + "'qwe' was added successfully.*";
        assertTrue(viewModel.getLogMessages().get(1).matches(messageRegexp));
    }

    @Test
    public void testThatRemovingEmptyValueProducesCorrectLogs() {
        viewModel.setValue("");
        viewModel.remove();
        String messageRegexp = ".*" + REMOVE_BUTTON_PRESSED + "Value is empty!.*";
        assertTrue(viewModel.getLogMessages().get(2).matches(messageRegexp));
    }

    @Test
    public void testThatSearchingEmptyValueProducesCorrectLogs() {
        viewModel.setValue("");
        viewModel.search();
        String messageRegexp = ".*" + SEARCH_BUTTON_PRESSED + "Value is empty!.*";
        assertTrue(viewModel.getLogMessages().get(2).matches(messageRegexp));
    }

    @Test
    public void testThatSearchingInEmptyQueueProducesCorrectLogs() {
        deleteValuesFromQueue();
        viewModel.search();
        String messageRegexp = ".*" + SEARCH_BUTTON_PRESSED + "Queue is empty!.*";
        assertTrue(viewModel.getLogMessages().get(4).matches(messageRegexp));
    }

    @Test
    public void testThatUserActionsProduceLog() {
        assertFalse(viewModel.getLog().isEmpty());
    }

    @Test
    public void testThatCanSetValue() {
        viewModel.setValue("value");
        assertEquals("value", viewModel.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatCanNotSetNullLogger() {
        viewModel.setLogger(null);
    }

    private void deleteValuesFromQueue() {
        viewModel.remove();
        viewModel.setValue("123aaa");
        viewModel.remove();
    }
}
