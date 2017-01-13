package ru.unn.agile.Huffman.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class HuffmanViewModelTest {
    private HuffmanViewModel viewModel;

    public void setViewModel(final HuffmanViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new HuffmanViewModel(new FakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void testCanInitEmptyModel() {
        HuffmanViewModel testViewModel = new HuffmanViewModel();
        assertEquals("", testViewModel.getStatus());
    }

    @Test
    public void testCanSetDefaultValues() {
        assertEquals("", viewModel.enterStringProperty().get());
        assertEquals("", viewModel.getLogs());
        assertEquals("", viewModel.getLogsProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void testSuccessfulDecodedResult() {
        viewModel.enterStringProperty().set("abbab");
        viewModel.decodeString();
        assertEquals("01101", viewModel.decodeResultProperty().get());
        assertEquals(Status.STRING_DECODED.toString(), viewModel.getStatus());
    }

    @Test
    public void testFailedDecodedResult() {
        viewModel.enterStringProperty().set(null);
        viewModel.decodeString();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void testSuccessfulEncodeDecodedString() {
        viewModel.enterStringProperty().set("abcde");
        viewModel.decodeString();
        viewModel.enterStringProperty().set(viewModel.getDecodeResult());
        viewModel.encodeString();
        assertEquals("abcde", viewModel.encodeResultProperty().get());
    }

    @Test
    public void testGetFailedStatusIfEncodedStringIsNotAddedBefore() {
        viewModel.enterStringProperty().set("abcde");
        viewModel.decodeString();
        viewModel.enterStringProperty().set(viewModel.getDecodeResult() + "0111");
        viewModel.encodeString();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void testSuccessfulEncodeDecodedResult() {
        viewModel.enterStringProperty().set("abbcccddddeeeee");
        viewModel.decodeString();
        viewModel.enterStringProperty().set("010011001011");
        viewModel.encodeString();
        assertEquals("abcde", viewModel.getEncodeResult());
        assertEquals(Status.STRING_ENCODED.toString(), viewModel.getStatus());
    }

    @Test
    public void testFailedEncodedResult() {
        viewModel.enterStringProperty().set("010011001011");
        viewModel.encodeString();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void viewModelConstructorThrowsException() {
        try {
            new HuffmanViewModel(null);
            fail("Exception did not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("logcreator parameter can't be null", e.getMessage());
        } catch (Exception e) {
            fail("Wrong exception type");
        }
    }

    @Test
    public void logIsEmpty() {
        List<String> log = viewModel.getLogCreator();
        assertTrue(log.isEmpty());
    }

    @Test
    public void testLogMessageCorrectReturns() {
        assertEquals("Decode string: ", Messages.DECODE);
    }

    @Test
    public void logContainsMessageAfter() {
        viewModel.enterStringProperty().set("010011001011");
        viewModel.decodeString();
        String message = viewModel.getLogCreator().get(0);

        assertTrue(message.matches(".*" + Messages.DECODE + ".*"));
    }

}
