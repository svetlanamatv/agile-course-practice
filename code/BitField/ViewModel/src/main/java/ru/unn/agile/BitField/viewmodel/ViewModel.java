package ru.unn.agile.BitField.viewmodel;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.BitField.model.BitField;

import java.io.IOException;

import java.util.List;


public class ViewModel {
    private static final int LENGTH_BIT_FIELD = 8;

    private BitField bitFieldA = new BitField(LENGTH_BIT_FIELD);
    private BitField bitFieldB = new BitField(LENGTH_BIT_FIELD);
    private BitField bitFieldResult = new BitField(LENGTH_BIT_FIELD);

    private final StringProperty bitFieldStringA = new SimpleStringProperty();
    private final StringProperty chooseBitA = new SimpleStringProperty();
    private final StringProperty textErrorA = new SimpleStringProperty();

    private final StringProperty bitFieldStringB = new SimpleStringProperty();
    private final StringProperty chooseBitB = new SimpleStringProperty();
    private final StringProperty textErrorB = new SimpleStringProperty();

    private final StringProperty resultText = new SimpleStringProperty();
    private final StringProperty myRealLogs = new SimpleStringProperty();

    private TheLog mainWindowLogger;

    public final void setThisOneLog(final TheLog someLogger) {
        if (someLogger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.mainWindowLogger = someLogger;
    }

    public ViewModel() {
        init();
    }

    public ViewModel(final TheLog someOneLog) {
        setThisOneLog(someOneLog);
        init();
    }

    private void init() {
        bitFieldStringA.set(bitFieldA.toString());
        bitFieldStringB.set(bitFieldB.toString());
    }

    // Methods

    public void setBitFieldStringA(final String bitField) throws IOException {
        if (!canInputBitFieldCur(bitField, textErrorA)) {
            return;
        }

        String correctBitField = correctionBitField(bitField);

        bitFieldA = BitField.fromString(correctBitField);
        bitFieldStringA.set(bitFieldA.toString());
    }

    public void setBitFieldStringB(final String bitField) throws IOException {
        if (!canInputBitFieldCur(bitField, textErrorB)) {
            return;
        }

        String correctBitField = correctionBitField(bitField);

        bitFieldB = BitField.fromString(correctBitField);
        bitFieldStringB.set(bitFieldB.toString());
    }

    public void setBitFieldBitA(final String numOfBit) throws IOException {
        setBitFieldBitCur(numOfBit, bitFieldA, bitFieldStringA);
    }

    public void setBitFieldBitB(final String numOfBit) throws IOException {
        setBitFieldBitCur(numOfBit, bitFieldB, bitFieldStringB);
    }

    public void clearBitFieldBitA(final String numOfBit) throws IOException {
        clearBitFieldBitCur(numOfBit, bitFieldA, bitFieldStringA);
    }

    public void clearBitFieldBitB(final String numOfBit) throws IOException {
        clearBitFieldBitCur(numOfBit, bitFieldB, bitFieldStringB);
    }

    public void getBitFieldBitA(final String numOfBit) throws IOException {
        getBitFieldBitCur(numOfBit, bitFieldA, chooseBitA);
    }

    public void getBitFieldBitB(final String numOfBit) throws IOException {
        getBitFieldBitCur(numOfBit, bitFieldB, chooseBitB);
    }

    public void logicNotA() throws IOException {
        BitField field = new BitField(bitFieldA);
        bitFieldA = field.not();

        bitFieldStringA.set(bitFieldA.toString());
        StringBuilder message = new StringBuilder(Message.NOT_A_WAS_PRESSED);
        message.append("Not A operation used").append(".");
        mainWindowLogger.log(message.toString());
        logUpdating();
    }

    public void logicNotB() throws IOException {
        BitField field = new BitField(bitFieldB);
        bitFieldB = field.not();

        bitFieldStringB.set(bitFieldB.toString());
        StringBuilder message = new StringBuilder(Message.NOT_B_WAS_PRESSED);
        message.append("Not B operation used").append(".");
        mainWindowLogger.log(message.toString());
        logUpdating();
    }

    private String correctionBitField(final String bitField) throws IOException {
        String correctBitField = new String();
        int lenBitField = bitField.length();

        for (int i = 0; i < LENGTH_BIT_FIELD - lenBitField; i++) {
            correctBitField += "0";
        }

        correctBitField += bitField;
        StringBuilder message = new StringBuilder(Message.CORRECTING_BIT_FIELD);
        message.append("BitField was correced!").append(".");
        mainWindowLogger.log(message.toString());
        logUpdating();
        return correctBitField;
    }

    private boolean canInputBitFieldCur(final String bitField,
                                        final StringProperty textErrorCur) throws IOException {
        if ("".equals(bitField)) {
            textErrorCur.set("Text Field is Empty");
            StringBuilder message = new StringBuilder(Message.INPUT_WAS_PRESSED_BUT_INCORRECT);
            message.append("Input is incorrect!").append(".");
            mainWindowLogger.log(message.toString());
            logUpdating();
            return false;
        }

        if (!bitField.matches("[01]+")) {
            textErrorCur.set("Only 0 and 1");
            StringBuilder message = new StringBuilder(Message.INPUT_WAS_PRESSED_BUT_INCORRECT);
            message.append("Input is incorrect!").append(".");
            mainWindowLogger.log(message.toString());
            logUpdating();
            return false;
        }

        int lenBitField = bitField.length();
        if (lenBitField > LENGTH_BIT_FIELD) {
            textErrorCur.set("Length of BitField must be less or equal 8");
            StringBuilder message = new StringBuilder(Message.INPUT_WAS_PRESSED_BUT_INCORRECT);
            message.append("Input is incorrect!").append(".");
            mainWindowLogger.log(message.toString());
            logUpdating();
            return false;
        }

        textErrorCur.set("");
        StringBuilder message = new StringBuilder(Message.INPUT_WAS_PRESSED);
        message.append("Input success!").append(".");
        mainWindowLogger.log(message.toString());
        logUpdating();
        return true;
    }

    public void setBitFieldBitCur(final String numOfBit, final BitField bitFieldCur,
                                  final StringProperty bitFieldStringCur) throws IOException {
        int numOfBitInt = Integer.parseInt(numOfBit);
        bitFieldCur.setBit(numOfBitInt);

        bitFieldStringCur.set(bitFieldCur.toString());
        StringBuilder message = new StringBuilder(Message.SET_BIT_WAS_PRESSED);
        message.append("Set was pressed").append(".");
        mainWindowLogger.log(message.toString());
        logUpdating();
    }

    public void clearBitFieldBitCur(final String numOfBit, final BitField bitFieldCur,
                                    final StringProperty bitFieldStringCur) throws IOException {
        int numOfBitInt = Integer.parseInt(numOfBit);
        bitFieldCur.clrBit(numOfBitInt);

        bitFieldStringCur.set(bitFieldCur.toString());
        StringBuilder message = new StringBuilder(Message.CLEAR_BIT_WAS_PRESSED);
        message.append("Clear was pressed").append(".");
        mainWindowLogger.log(message.toString());
        logUpdating();
    }

    public void getBitFieldBitCur(final String numOfBit, final BitField bitFieldCur,
                                  final StringProperty chooseBitCur) throws IOException {
        int numOfBitInt = Integer.parseInt(numOfBit);
        int chooseBit = bitFieldCur.getBit(numOfBitInt);

        chooseBitCur.set(Integer.toString(chooseBit));
        StringBuilder message = new StringBuilder(Message.GET_BIT_WAS_PRESSED);
        message.append("Get was pressed").append(".");
        mainWindowLogger.log(message.toString());
        logUpdating();
    }

    public void logicAAndB() throws IOException {
        bitFieldResult = new BitField(bitFieldA);
        bitFieldResult = bitFieldA.and(bitFieldB);

        resultText.set(bitFieldResult.toString());

        StringBuilder message = new StringBuilder(Message.AND_WAS_PRESSED);
        message.append("Arguments")
                .append(": Field A = ").append(getBitFieldStringA())
                .append("; Field B = ").append(getBitFieldStringB())
                .append(" Operation: ").append("AND").append(".");
        mainWindowLogger.log(message.toString());
        logUpdating();
    }

    public void logicAOrB() throws IOException {
        bitFieldResult = new BitField(bitFieldA);
        bitFieldResult = bitFieldA.or(bitFieldB);

        resultText.set(bitFieldResult.toString());

        StringBuilder message = new StringBuilder(Message.OR_WAS_PRESSED);
        message.append("Arguments")
                .append(": Field A = ").append(getBitFieldStringA())
                .append("; Field B = ").append(getBitFieldStringB())
                .append(" Operation: ").append("OR").append(".");
        mainWindowLogger.log(message.toString());
        logUpdating();
    }

    public void logicAXorB() throws IOException {
        bitFieldResult = new BitField(bitFieldA);
        bitFieldResult = bitFieldA.xor(bitFieldB);

        resultText.set(bitFieldResult.toString());

        StringBuilder message = new StringBuilder(Message.XOR_WAS_PRESSED);
        message.append("Arguments")
                .append(": Field A = ").append(getBitFieldStringA())
                .append("; Field B = ").append(getBitFieldStringB())
                .append(" Operation: ").append("XOR").append(".");
        mainWindowLogger.log(message.toString());
        logUpdating();
    }

    private void logUpdating() {
        List<String> fullLog = mainWindowLogger.getThisLog();
        String record = new String();
        for (String log : fullLog) {
            record += log + "\n";
        }
        myRealLogs.set(record);
    }

    // Property Getters Fields

    public StringProperty bitFieldStringAProperty() {
        return bitFieldStringA;
    }

    public StringProperty bitFieldStringBProperty() {
        return bitFieldStringB;
    }

    public StringProperty chooseBitAProperty() {
        return chooseBitA;
    }

    public StringProperty chooseBitBProperty() {
        return chooseBitB;
    }

    public StringProperty textErrorAProperty() {
        return textErrorA;
    }

    public StringProperty textErrorBProperty() {
        return textErrorB;
    }

    public StringProperty resultTextProperty() {
        return resultText;
    }

    public StringProperty myRealLogsProperty() {
        return myRealLogs;
    }

    // Getters Fields

    public final String getBitFieldStringA() {
        return bitFieldStringA.get();
    }

    public final String getBitFieldStringB() {
        return bitFieldStringB.get();
    }

    public final String getChooseBitA() {
        return chooseBitA.get();
    }

    public final String getChooseBitB() {
        return chooseBitB.get();
    }

    public final String getTextErrorA() {
        return textErrorA.get();
    }

    public final String getTextErrorB() {
        return textErrorB.get();
    }

    public final String getResultText() {
        return resultText.get();
    }

    public final List<String> getThisNiceLog() {
        return mainWindowLogger.getThisLog();
    }

    public final String getMyRealLogs() {
        return myRealLogs.get();
    }
}

final class Message {
    public static final String XOR_WAS_PRESSED = "Xor. ";
    public static final String OR_WAS_PRESSED = "Or. ";
    public static final String AND_WAS_PRESSED = "And. ";
    public static final String NOT_A_WAS_PRESSED = "Not A. ";
    public static final String NOT_B_WAS_PRESSED = "Not B. ";
    public static final String SET_BIT_WAS_PRESSED = "Set. ";
    public static final String GET_BIT_WAS_PRESSED = "Get. ";
    public static final String CLEAR_BIT_WAS_PRESSED = "Clear. ";
    public static final String INPUT_WAS_PRESSED = "Input. ";
    public static final String INPUT_WAS_PRESSED_BUT_INCORRECT = "Incorrect input. ";
    public static final String CORRECTING_BIT_FIELD = "Correcting BitField. ";

    private Message() {
    }
}

