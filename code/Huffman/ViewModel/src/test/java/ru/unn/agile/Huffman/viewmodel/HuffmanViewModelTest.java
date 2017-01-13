package ru.unn.agile.Huffman.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HuffmanViewModelTest {
    private HuffmanViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new HuffmanViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void testCanSetDefaultValues() {
        assertEquals("", viewModel.enterStringProperty().get());
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
        assertEquals(Status.BAD_FORMAT.toString(),viewModel.getStatus());
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
        viewModel.enterStringProperty().set(viewModel.getDecodeResult()+"0111");
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

}
