package ru.unn.agile.BinaryTree.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FakeLoggerTests {
    private BinaryTreeViewModel viewModel;

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new BinaryTreeViewModel(new FakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void viewModelConstructorThrowsException() {
        try {
            new BinaryTreeViewModel(null);
            fail("Exception do not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Logger parameter can't be null", e.getMessage());
        } catch (Exception e) {
            fail("Wrong exception type");
        }
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLogger();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logMessageIsCorrectReturns() {
        assertEquals("Add Node. ", LogMessages.ADD_NODE_PRESSED);
    }

    @Test
    public void logContainsMessageAfterAddNode() {
        setInputData();
        viewModel.addNodeToTree();
        String message = viewModel.getLogger().get(0);

        assertTrue(message.matches(".*" + LogMessages.ADD_NODE_PRESSED + ".*"));
    }

    @Test
    public void logContainsMessageAfterRemNode() {
        setInputData();
        viewModel.remNodeFromTree();
        String message = viewModel.getLogger().get(0);

        assertTrue(message.matches(".*" + LogMessages.REM_NODE_PRESSED + ".*"));
    }

    @Test
    public void testArgumentsAreCorrectlyLoggedAfterAddNode() {
        setInputData();
        viewModel.addNodeToTree();
        String message = viewModel.getLogger().get(0);
        assertTrue(message.matches(".*" + LogMessages.ADD_NODE_PRESSED
                + viewModel.addNodeProperty().get()
                + " to Tree! "
                + Status.ELEMENT_ADDED.toString()));
    }

    private void setInputData() {
        viewModel.addNodeProperty().set("1");
        viewModel.remNodeProperty().set("1");
        viewModel.getNodeProperty().set("1");
        viewModel.searchProperty().set("1");
    }
}
