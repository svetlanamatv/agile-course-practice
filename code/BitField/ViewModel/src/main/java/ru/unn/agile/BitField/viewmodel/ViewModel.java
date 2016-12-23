package ru.unn.agile.BitField.viewmodel;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.BitField.model.BitField;

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
    private List<ListenerForCachChangesInValue> valueChangedListeners;

    public final void setThisOneLog(final TheLog SomeLogger) {
        if (SomeLogger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.mainWindowLogger = SomeLogger;
    }

    public ViewModel() {
        bitFieldStringA.set(bitFieldA.toString());
        bitFieldStringB.set(bitFieldB.toString());
    }

    public ViewModel(final TheLog SomeOneLog) {
        setThisOneLog(SomeOneLog);
        bitFieldStringA.set(bitFieldA.toString());
        bitFieldStringB.set(bitFieldB.toString());
    }

    // Methods

    public void setBitFieldStringA(final String bitField) {
        if (!canInputBitFieldCur(bitField, textErrorA)) {
            return;
        }

        String correctBitField = correctionBitField(bitField);

        bitFieldA = BitField.fromString(correctBitField);
        bitFieldStringA.set(bitFieldA.toString());
    }

    public void setBitFieldStringB(final String bitField) {
        if (!canInputBitFieldCur(bitField, textErrorB)) {
            return;
        }

        String correctBitField = correctionBitField(bitField);

        bitFieldB = BitField.fromString(correctBitField);
        bitFieldStringB.set(bitFieldB.toString());
    }

    public void setBitFieldBitA(final String numOfBit) {
        setBitFieldBitCur(numOfBit, bitFieldA, bitFieldStringA);
    }

    public void setBitFieldBitB(final String numOfBit) {
        setBitFieldBitCur(numOfBit, bitFieldB, bitFieldStringB);
    }

    public void clearBitFieldBitA(final String numOfBit) {
        clearBitFieldBitCur(numOfBit, bitFieldA, bitFieldStringA);
    }

    public void clearBitFieldBitB(final String numOfBit) {
        clearBitFieldBitCur(numOfBit, bitFieldB, bitFieldStringB);
    }

    public void getBitFieldBitA(final String numOfBit) {
        getBitFieldBitCur(numOfBit, bitFieldA, chooseBitA);
    }

    public void getBitFieldBitB(final String numOfBit) {
        getBitFieldBitCur(numOfBit, bitFieldB, chooseBitB);
    }

    public void logicNotA() {
        BitField field = new BitField(bitFieldA);
        bitFieldA = field.not();

        bitFieldStringA.set(bitFieldA.toString());
    }

    public void logicNotB() {
        BitField field = new BitField(bitFieldB);
        bitFieldB = field.not();

        bitFieldStringB.set(bitFieldB.toString());
    }

    private String correctionBitField(final String bitField) {
        String correctBitField = new String();
        int lenBitField = bitField.length();

        for (int i = 0; i < LENGTH_BIT_FIELD - lenBitField; i++) {
            correctBitField += "0";
        }

        correctBitField += bitField;
        return correctBitField;
    }

    private boolean canInputBitFieldCur(final String bitField, final StringProperty textErrorCur) {
        if ("".equals(bitField)) {
            textErrorCur.set("Text Field is Empty");
            return false;
        }

        if (!bitField.matches("[01]+")) {
            textErrorCur.set("Only 0 and 1");
            return false;
        }

        int lenBitField = bitField.length();
        if (lenBitField > LENGTH_BIT_FIELD) {
            textErrorCur.set("Length of BitField must be less or equal 8");
            return false;
        }

        textErrorCur.set("");
        return true;
    }

    public void setBitFieldBitCur(final String numOfBit, final BitField bitFieldCur,
                                  final StringProperty bitFieldStringCur) {
        int numOfBitInt = Integer.parseInt(numOfBit);
        bitFieldCur.setBit(numOfBitInt);

        bitFieldStringCur.set(bitFieldCur.toString());
    }

    public void clearBitFieldBitCur(final String numOfBit, final BitField bitFieldCur,
                                    final StringProperty bitFieldStringCur) {
        int numOfBitInt = Integer.parseInt(numOfBit);
        bitFieldCur.clrBit(numOfBitInt);

        bitFieldStringCur.set(bitFieldCur.toString());
    }

    public void getBitFieldBitCur(final String numOfBit, final BitField bitFieldCur,
                                  final StringProperty chooseBitCur) {
        int numOfBitInt = Integer.parseInt(numOfBit);
        int chooseBit = bitFieldCur.getBit(numOfBitInt);

        chooseBitCur.set(Integer.toString(chooseBit));
    }

    public void logicAAndB() {
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

    public void logicAOrB() {
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

    public void logicAXorB() {
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

    private class ListenerForCachChangesInValue implements ChangeListener<String> {
        private String valueCurrent = new String();

        @Override
        public void changed(final ObservableValue<? extends String> obs,
                            final String valueolding, final String newState) {
            if (valueolding.equals(newState)) {
                return;
            }
            valueCurrent = newState;
        }
    }
}

final class Message {
    public static final String XOR_WAS_PRESSED = "Xor. ";
    public static final String OR_WAS_PRESSED = "Or. ";
    public static final String AND_WAS_PRESSED = "And. ";

    private Message() {
    }
}

