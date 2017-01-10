package ru.unn.agile.BitField.viewmodel;

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
        viewModel.setBitFieldStringA("");

        assertEquals("Text Field is Empty", viewModel.getTextErrorA());
    }

    @Test
    public void whenEnterEmptyBErrorText() {
        viewModel.setBitFieldStringB("");

        assertEquals("Text Field is Empty", viewModel.getTextErrorB());
    }


    @Test
    public void whenEnterFieldWrongDataAErrorText() {
        viewModel.setBitFieldStringA("2");

        assertEquals("Only 0 and 1", viewModel.getTextErrorA());
    }

    @Test
    public void whenEnterFieldWrongDataBErrorText() {
        viewModel.setBitFieldStringB("a");

        assertEquals("Only 0 and 1", viewModel.getTextErrorB());
    }

    @Test
    public void inputBitFieldALengthMoreLengthBitFieldErrorText() {
        viewModel.setBitFieldStringA("111111111");

        assertEquals("Length of BitField must be less or equal 8", viewModel.getTextErrorA());
    }

    @Test
    public void inputBitFieldBLengthMoreLengthBitFieldErrorText() {
        viewModel.setBitFieldStringB("000000000");

        assertEquals("Length of BitField must be less or equal 8", viewModel.getTextErrorB());
    }

    @Test
    public void setBitFieldALengthEqualLengthBitField() {
        viewModel.setBitFieldStringA("01010101");

        assertEquals("01010101", viewModel.getBitFieldStringA());
    }

    @Test
    public void setBitFieldBLengthEqualLengthBitField() {
        viewModel.setBitFieldStringB("10101010");

        assertEquals("10101010", viewModel.getBitFieldStringB());
    }

    @Test
    public void setBitFieldALengthLessLengthBitField() {
        viewModel.setBitFieldStringA("1111");

        assertEquals("00001111", viewModel.getBitFieldStringA());
    }

    @Test
    public void setBitFieldBLengthLessLengthBitField() {
        viewModel.setBitFieldStringB("111100");

        assertEquals("00111100", viewModel.getBitFieldStringB());
    }

    @Test
    public void setBitFieldBitA() {
        viewModel.setBitFieldBitA("5");

        assertEquals("00000100", viewModel.getBitFieldStringA());
    }

    @Test
    public void setBitFieldBitB() {
        viewModel.setBitFieldBitB("3");

        assertEquals("00010000", viewModel.getBitFieldStringB());
    }

    @Test
    public void setBitFieldBitA3Bits() {
        viewModel.setBitFieldBitA("1");
        viewModel.setBitFieldBitA("2");
        viewModel.setBitFieldBitA("7");

        assertEquals("01100001", viewModel.getBitFieldStringA());
    }

    @Test
    public void setBitFieldBitB3Bits() {
        viewModel.setBitFieldBitB("0");
        viewModel.setBitFieldBitB("3");
        viewModel.setBitFieldBitB("6");

        assertEquals("10010010", viewModel.getBitFieldStringB());
    }

    @Test
    public void clearBitFieldBitA() {
        viewModel.setBitFieldStringA("11111111");
        viewModel.clearBitFieldBitA("5");

        assertEquals("11111011", viewModel.getBitFieldStringA());
    }

    @Test
    public void clearBitFieldBitB() {
        viewModel.setBitFieldStringB("11111111");
        viewModel.clearBitFieldBitB("3");

        assertEquals("11101111", viewModel.getBitFieldStringB());
    }

    @Test
    public void clearBitFieldBitA3Bits() {
        viewModel.setBitFieldStringA("11111111");
        viewModel.clearBitFieldBitA("1");
        viewModel.clearBitFieldBitA("2");
        viewModel.clearBitFieldBitA("7");

        assertEquals("10011110", viewModel.getBitFieldStringA());
    }

    @Test
    public void clearBitFieldBitB3Bits() {
        viewModel.setBitFieldStringB("11111111");
        viewModel.clearBitFieldBitB("0");
        viewModel.clearBitFieldBitB("3");
        viewModel.clearBitFieldBitB("6");

        assertEquals("01101101", viewModel.getBitFieldStringB());
    }

    @Test
    public void getBitFieldBitAOne() {
        viewModel.setBitFieldStringA("00001111");
        viewModel.getBitFieldBitA("4");

        assertEquals("1", viewModel.getChooseBitA());
    }

    @Test
    public void getBitFieldBitBOne() {
        viewModel.setBitFieldStringB("00001111");
        viewModel.getBitFieldBitB("5");

        assertEquals("1", viewModel.getChooseBitB());
    }

    @Test
    public void getBitFieldBitAZero() {
        viewModel.setBitFieldStringA("00001111");
        viewModel.getBitFieldBitA("3");

        assertEquals("0", viewModel.getChooseBitA());
    }

    @Test
    public void getBitFieldBitBZero() {
        viewModel.setBitFieldStringB("00001111");
        viewModel.getBitFieldBitB("2");

        assertEquals("0", viewModel.getChooseBitB());
    }

    @Test
    public void notA() {
        viewModel.setBitFieldStringA("00010111");
        viewModel.logicNotA();

        assertEquals("11101000", viewModel.getBitFieldStringA());
    }

    @Test
    public void notB() {
        viewModel.setBitFieldStringB("10101010");
        viewModel.logicNotB();

        assertEquals("01010101", viewModel.getBitFieldStringB());
    }

    @Test
    public void resultAAndB() {
        viewModel.setBitFieldStringA("10101010");
        viewModel.setBitFieldStringB("00001111");
        viewModel.logicAAndB();

        assertEquals("00001010", viewModel.getResultText());
    }

    @Test
    public void resultAAndB2() {
        viewModel.setBitFieldStringA("10101011");
        viewModel.setBitFieldStringB("01010101");
        viewModel.logicAAndB();

        assertEquals("00000001", viewModel.getResultText());
    }

    @Test
    public void resultAAndB3() {
        viewModel.setBitFieldStringA("10101011");
        viewModel.setBitFieldStringB("01011100");
        viewModel.logicAAndB();

        assertEquals("00001000", viewModel.getResultText());
    }

    @Test
    public void resultAOrB() {
        viewModel.setBitFieldStringA("10101010");
        viewModel.setBitFieldStringB("00001111");
        viewModel.logicAOrB();

        assertEquals("10101111", viewModel.getResultText());
    }

    @Test
    public void resultAOrB1() {
        viewModel.setBitFieldStringA("10101010");
        viewModel.setBitFieldStringB("10011100");
        viewModel.logicAOrB();

        assertEquals("10111110", viewModel.getResultText());
    }

    @Test
    public void resultAOrB2() {
        viewModel.setBitFieldStringA("10101010");
        viewModel.setBitFieldStringB("01010101");
        viewModel.logicAOrB();

        assertEquals("11111111", viewModel.getResultText());
    }

    @Test
    public void resultAXorB() {
        viewModel.setBitFieldStringA("10101010");
        viewModel.setBitFieldStringB("00001111");
        viewModel.logicAXorB();

        assertEquals("10100101", viewModel.getResultText());
    }

    @Test
    public void resultAXorB1() {
        viewModel.setBitFieldStringA("10111011");
        viewModel.setBitFieldStringB("01001111");
        viewModel.logicAXorB();

        assertEquals("11110100", viewModel.getResultText());
    }

    @Test
    public void resultAXorB2() {
        viewModel.setBitFieldStringA("10101010");
        viewModel.setBitFieldStringB("01010101");
        viewModel.logicAXorB();

        assertEquals("11111111", viewModel.getResultText());
    }

    private void setA() {
        viewModel.setBitFieldStringA("11001100");
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
        viewModel.logicAXorB();
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.XOR_WAS_PRESSED + ".*"));
    }

    @Test
    public void logCanCreateMessAfterOrOperation() {
        viewModel.logicAOrB();
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.OR_WAS_PRESSED + ".*"));
    }

    @Test
    public void logCanCreateMessAfterAndOperation() {
        viewModel.logicAAndB();
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.AND_WAS_PRESSED + ".*"));
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
        viewModel.setBitFieldStringA("11111111");
        viewModel.setBitFieldStringB("00000000");

        assertNotNull(viewModel.getMyRealLogs());
    }

    @Test
    public void canCreateMessageTest() {
        Message message;
    }

    @Test
    public void logCanCreateMessAfterNotAOperation() {
        viewModel.logicNotA();
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.NOT_A_WAS_PRESSED + ".*"));
    }

    @Test
    public void logCanCreateMessAfterNotBOperation() {
        viewModel.logicNotB();
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.NOT_B_WAS_PRESSED + ".*"));
    }

    @Test
    public void logCanCreateMessAfterInputOperation() {
        setA();
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.INPUT_WAS_PRESSED + ".*"));
    }

    @Test
    public void logCanCreateMessAfterIncorrectValueInput() {
        viewModel.setBitFieldStringA("00000500");
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.INPUT_WAS_PRESSED_BUT_INCORRECT + ".*"));
    }

    @Test
    public void logCanCreateMessAfterIncorrectSizeInput() {
        viewModel.setBitFieldStringA("0010010011001");
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.INPUT_WAS_PRESSED_BUT_INCORRECT + ".*"));
    }

    @Test
    public void logCanCreateMessAfterNullInput() {
        viewModel.setBitFieldStringA("");
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.INPUT_WAS_PRESSED_BUT_INCORRECT + ".*"));
    }

    @Test
    public void logCanCreateSuccessMessAfterCorrectingBitField() {
        viewModel.setBitFieldStringA("00101");
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.INPUT_WAS_PRESSED + ".*"));
    }

    @Test
    public void logCanCreateSuccessMessAfterClearBitsA() {
        viewModel.clearBitFieldBitA("1");
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.CLEAR_BIT_WAS_PRESSED + ".*"));
    }

    @Test
    public void logCanCreateSuccessMessAfterSettingBitsA() {
        viewModel.setBitFieldBitA("4");
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.SET_BIT_WAS_PRESSED + ".*"));
    }

    @Test
    public void logCanCreateSuccessMessAfterGettingBitsA() {
        viewModel.getBitFieldBitA("2");
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.GET_BIT_WAS_PRESSED + ".*"));
    }

    @Test
    public void logCanCreateSuccessMessAfterClearBitsB() {
        viewModel.clearBitFieldBitB("7");
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.CLEAR_BIT_WAS_PRESSED + ".*"));
    }

    @Test
    public void logCanCreateSuccessMessAfterSettingBitsB() {
        viewModel.setBitFieldBitB("5");
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.SET_BIT_WAS_PRESSED + ".*"));
    }

    @Test
    public void logCanCreateSuccessMessAfterGettingBitsB() {
        viewModel.getBitFieldBitB("1");
        String message = viewModel.getThisNiceLog().get(0);

        assertTrue(message.matches(".*" + Message.GET_BIT_WAS_PRESSED + ".*"));
    }

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
