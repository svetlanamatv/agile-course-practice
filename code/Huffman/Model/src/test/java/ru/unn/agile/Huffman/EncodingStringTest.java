package ru.unn.agile.Huffman;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class EncodingStringTest {
    private HuffmanAlg huffmanAlg;
    @Before
    public void setUp() {
        huffmanAlg = new HuffmanAlg();
    }

    @Test
    public void emptyStrGivesEmpty() {
        assertEncodeOutput("", "");
    }

    @Test
    public void oneSymbolStrGivesZero() {
        assertEncodeOutput("1", "a");
    }

    @Test
    public void twoSymbolsStrGivesOneBitSymbolCode() {
        assertEncodeOutput("01101", "abbab");
    }

    @Test
    public void fiveCharsStrWithDifferentFreqsGivesCorrectResult() {
        assertEncodeOutput("010011011000000101010101111111111", "abbcccddddeeeee");
    }

    @Test
    public void nullInputGivesException() {
        try {
            huffmanAlg.encodeString(null);
        } catch (IllegalArgumentException caughtException) {
            assertThat(caughtException.getMessage(), is("NULL input! "
                    + "Use encode/decode methods for string arguments."));
        }
    }

    private void assertEncodeOutput(final String expected, final String input) {
        assertEquals(expected, huffmanAlg.encodeString(input));
    }
}
