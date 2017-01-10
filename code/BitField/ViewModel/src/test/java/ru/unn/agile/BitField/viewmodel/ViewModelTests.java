package ru.unn.agile.BitField.viewmodel;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new ViewModel(new OneFakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void byDefaultFieldA() {
        assertEquals("00000000", viewModel.getBitFieldStringA());
    }

    @Test
    public void byDefaultFieldB() {
        assertEquals("00000000", viewModel.getBitFieldStringB());
    }

    @Test
    public void whenEnterEmptyAErrorText() {
        try {
            viewModel.setBitFieldStringA("");

            assertEquals("Text Field is Empty", viewModel.getTextErrorA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenEnterEmptyBErrorText() {
        try {
            viewModel.setBitFieldStringB("");

            assertEquals("Text Field is Empty", viewModel.getTextErrorB());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void whenEnterFieldWrongDataAErrorText() {
        try {
            viewModel.setBitFieldStringA("2");

            assertEquals("Only 0 and 1", viewModel.getTextErrorA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenEnterFieldWrongDataBErrorText() {
        try {
            viewModel.setBitFieldStringB("a");

            assertEquals("Only 0 and 1", viewModel.getTextErrorB());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void inputBitFieldALengthMoreLengthBitFieldErrorText() {
        try {
            viewModel.setBitFieldStringA("111111111");

            assertEquals("Length of BitField must be less or equal 8", viewModel.getTextErrorA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void inputBitFieldBLengthMoreLengthBitFieldErrorText() {
        try {
            viewModel.setBitFieldStringB("000000000");

            assertEquals("Length of BitField must be less or equal 8", viewModel.getTextErrorB());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setBitFieldALengthEqualLengthBitField() {
        try {
            viewModel.setBitFieldStringA("01010101");

            assertEquals("01010101", viewModel.getBitFieldStringA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setBitFieldBLengthEqualLengthBitField() {
        try {
            viewModel.setBitFieldStringB("10101010");

            assertEquals("10101010", viewModel.getBitFieldStringB());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setBitFieldALengthLessLengthBitField() {
        try {
            viewModel.setBitFieldStringA("1111");

            assertEquals("00001111", viewModel.getBitFieldStringA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setBitFieldBLengthLessLengthBitField() {
        try {
            viewModel.setBitFieldStringB("111100");

            assertEquals("00111100", viewModel.getBitFieldStringB());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setBitFieldBitA() {
        try {
            viewModel.setBitFieldBitA("5");

            assertEquals("00000100", viewModel.getBitFieldStringA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setBitFieldBitB() {
        try {
            viewModel.setBitFieldBitB("3");

            assertEquals("00010000", viewModel.getBitFieldStringB());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setBitFieldBitA3Bits() {
        try {
            viewModel.setBitFieldBitA("1");
            viewModel.setBitFieldBitA("2");
            viewModel.setBitFieldBitA("7");

            assertEquals("01100001", viewModel.getBitFieldStringA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setBitFieldBitB3Bits() {
        try {
            viewModel.setBitFieldBitB("0");
            viewModel.setBitFieldBitB("3");
            viewModel.setBitFieldBitB("6");

            assertEquals("10010010", viewModel.getBitFieldStringB());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearBitFieldBitA() {
        try {
            viewModel.setBitFieldStringA("11111111");
            viewModel.clearBitFieldBitA("5");

            assertEquals("11111011", viewModel.getBitFieldStringA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearBitFieldBitB() {
        try {
            viewModel.setBitFieldStringB("11111111");
            viewModel.clearBitFieldBitB("3");

            assertEquals("11101111", viewModel.getBitFieldStringB());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearBitFieldBitA3Bits() {
        try {
            viewModel.setBitFieldStringA("11111111");
            viewModel.clearBitFieldBitA("1");
            viewModel.clearBitFieldBitA("2");
            viewModel.clearBitFieldBitA("7");

            assertEquals("10011110", viewModel.getBitFieldStringA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearBitFieldBitB3Bits() {
        try {
            viewModel.setBitFieldStringB("11111111");
            viewModel.clearBitFieldBitB("0");
            viewModel.clearBitFieldBitB("3");
            viewModel.clearBitFieldBitB("6");

            assertEquals("01101101", viewModel.getBitFieldStringB());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBitFieldBitAOne() {
        try {
            viewModel.setBitFieldStringA("00001111");
            viewModel.getBitFieldBitA("4");

            assertEquals("1", viewModel.getChooseBitA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBitFieldBitBOne() {
        try {
            viewModel.setBitFieldStringB("00001111");
            viewModel.getBitFieldBitB("5");

            assertEquals("1", viewModel.getChooseBitB());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBitFieldBitAZero() {
        try {
            viewModel.setBitFieldStringA("00001111");
            viewModel.getBitFieldBitA("3");

            assertEquals("0", viewModel.getChooseBitA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBitFieldBitBZero() {
        try {
            viewModel.setBitFieldStringB("00001111");
            viewModel.getBitFieldBitB("2");

            assertEquals("0", viewModel.getChooseBitB());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void notA() {
        try {
            viewModel.setBitFieldStringA("00010111");
            viewModel.logicNotA();

            assertEquals("11101000", viewModel.getBitFieldStringA());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void notB() {
        try {
            viewModel.setBitFieldStringB("10101010");
            viewModel.logicNotB();

            assertEquals("01010101", viewModel.getBitFieldStringB());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void resultAAndB() {
        try {
            viewModel.setBitFieldStringA("10101010");
            viewModel.setBitFieldStringB("00001111");
            viewModel.logicAAndB();

            assertEquals("00001010", viewModel.getResultText());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void resultAAndB2() {
        try {
            viewModel.setBitFieldStringA("10101011");
            viewModel.setBitFieldStringB("01010101");
            viewModel.logicAAndB();

            assertEquals("00000001", viewModel.getResultText());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void resultAAndB3() {
        try {
            viewModel.setBitFieldStringA("10101011");
            viewModel.setBitFieldStringB("01011100");
            viewModel.logicAAndB();

            assertEquals("00001000", viewModel.getResultText());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void resultAOrB() {
        try {
            viewModel.setBitFieldStringA("10101010");
            viewModel.setBitFieldStringB("00001111");
            viewModel.logicAOrB();

            assertEquals("10101111", viewModel.getResultText());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void resultAOrB1() {
        try {
            viewModel.setBitFieldStringA("10101010");
            viewModel.setBitFieldStringB("10011100");
            viewModel.logicAOrB();

            assertEquals("10111110", viewModel.getResultText());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void resultAOrB2() {
        try {
            viewModel.setBitFieldStringA("10101010");
            viewModel.setBitFieldStringB("01010101");
            viewModel.logicAOrB();

            assertEquals("11111111", viewModel.getResultText());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void resultAXorB() {
        try {
            viewModel.setBitFieldStringA("10101010");
            viewModel.setBitFieldStringB("00001111");
            viewModel.logicAXorB();

            assertEquals("10100101", viewModel.getResultText());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void resultAXorB1() {
        try {
            viewModel.setBitFieldStringA("10111011");
            viewModel.setBitFieldStringB("01001111");
            viewModel.logicAXorB();

            assertEquals("11110100", viewModel.getResultText());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void resultAXorB2() {
        try {
            viewModel.setBitFieldStringA("10101010");
            viewModel.setBitFieldStringB("01010101");
            viewModel.logicAXorB();

            assertEquals("11111111", viewModel.getResultText());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    private void setA() {
        try {
            viewModel.setBitFieldStringA("11001100");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void nullLoggerIsItPossible() {
        try {
            new ViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void logInTheBegin() {
        List<String> log = viewModel.getThisNiceLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logCanCreateMessAfterXorOperation() {
        try {
            viewModel.logicAXorB();
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.XOR_WAS_PRESSED + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logCanCreateMessAfterOrOperation() {
        try {
            viewModel.logicAOrB();
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.OR_WAS_PRESSED + ".*"));
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void logCanCreateMessAfterAndOperation() {
        try {
            viewModel.logicAAndB();
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.AND_WAS_PRESSED + ".*"));
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void myRealLogsPropertyTest() {

        assertNotNull(viewModel.myRealLogsProperty());
    }

    @Test
    public void resultTextPropertyTest() {

        assertNotNull(viewModel.resultTextProperty());
    }

    @Test
    public void textErrorBPropertyTest() {

        assertNotNull(viewModel.textErrorBProperty());
    }

    @Test
    public void textErrorAPropertyTest() {

        assertNotNull(viewModel.textErrorAProperty());
    }

    @Test
    public void chooseBitAPropertyTest() {

        assertNotNull(viewModel.chooseBitAProperty());
    }

    @Test
    public void chooseBitBPropertyTest() {

        assertNotNull(viewModel.chooseBitBProperty());
    }

    @Test
    public void bitFieldStringBPropertyTest() {

        assertNotNull(viewModel.bitFieldStringBProperty());
    }

    @Test
    public void bitFieldStringAPropertyTest() {

        assertNotNull(viewModel.bitFieldStringAProperty());
    }

    @Test
    public void getMyRealLogsTest() {
        try {
            viewModel.setBitFieldStringA("11111111");
            viewModel.setBitFieldStringB("00000000");

            assertNotNull(viewModel.getMyRealLogs());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canCreateMessageTest() {
        Message message;
    }

    @Test
    public void logCanCreateMessAfterNotAOperation() {
        try {
            viewModel.logicNotA();
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.NOT_A_WAS_PRESSED + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logCanCreateMessAfterNotBOperation() {
        try {
            viewModel.logicNotB();
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.NOT_B_WAS_PRESSED + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logCanCreateMessAfterInputOperation() {
        setA();
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.INPUT_WAS_PRESSED + ".*"));
    }

    @Test
    public void logCanCreateMessAfterIncorrectValueInput() {
        try {
            viewModel.setBitFieldStringA("00000500");
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.INPUT_WAS_PRESSED_BUT_INCORRECT + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logCanCreateMessAfterIncorrectSizeInput() {
        try {
            viewModel.setBitFieldStringA("0010010011001");
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.INPUT_WAS_PRESSED_BUT_INCORRECT + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logCanCreateMessAfterNullInput() {
        try {
            viewModel.setBitFieldStringA("");
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.INPUT_WAS_PRESSED_BUT_INCORRECT + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logCanCreateSuccessMessAfterCorrectingBitField() {
        try {
            viewModel.setBitFieldStringA("00101");
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.INPUT_WAS_PRESSED + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logCanCreateSuccessMessAfterClearBitsA() {
        try {
            viewModel.clearBitFieldBitA("1");
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.CLEAR_BIT_WAS_PRESSED + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logCanCreateSuccessMessAfterSettingBitsA() {
        try {
            viewModel.setBitFieldBitA("4");
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.SET_BIT_WAS_PRESSED + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logCanCreateSuccessMessAfterGettingBitsA() {
        try {
            viewModel.getBitFieldBitA("2");
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.GET_BIT_WAS_PRESSED + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logCanCreateSuccessMessAfterClearBitsB() {
        try {
            viewModel.clearBitFieldBitB("7");
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.CLEAR_BIT_WAS_PRESSED + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logCanCreateSuccessMessAfterSettingBitsB() {
        try {
            viewModel.setBitFieldBitB("5");
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.SET_BIT_WAS_PRESSED + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logCanCreateSuccessMessAfterGettingBitsB() {
        try {
            viewModel.getBitFieldBitB("1");
            String message = viewModel.getThisNiceLog().get(0);

            assertTrue(message.matches(".*" + Message.GET_BIT_WAS_PRESSED + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
