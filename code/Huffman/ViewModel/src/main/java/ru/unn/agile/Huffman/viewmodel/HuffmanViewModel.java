package ru.unn.agile.Huffman.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.unn.agile.Huffman.HuffmanAlg;

import java.util.List;

public class HuffmanViewModel {
    private final StringProperty enterString = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty decodeResult = new SimpleStringProperty();
    private final HuffmanAlg huffman = new HuffmanAlg();
    private final StringProperty encodeResult = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();

    private ILogger logcreator;

    public StringProperty enterStringProperty() {
        return enterString;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getStatus() {
        return status.get();
    }

    public StringProperty decodeResultProperty() {
        return decodeResult;
    }

    public final String getDecodeResult() {
        return decodeResult.get();
    }

    public StringProperty encodeResultProperty() {
        return encodeResult;
    }

    public final String getEncodeResult() {
        return encodeResult.get();
    }

    public StringProperty getLogsProperty() {
        return logs;
    }

    public HuffmanViewModel() {
        initialize();
    }

    public final String getLogs() {
        return logs.get();
    }

    public final void setLogCreator(final ILogger logcreator) {
        if (logcreator == null) {
            throw new IllegalArgumentException("logcreator parameter can't be null");
        }
        this.logcreator = logcreator;
    }

    public HuffmanViewModel(final ILogger logcreator) {
        setLogCreator(logcreator);
        initialize();
        enterString.set("");
        status.set((Status.WAITING.toString()));
    }

    private void initialize() {
        enterString.set("");
        logs.set("");
        status.set((Status.WAITING.toString()));
    }

    public void decodeString() {
        StringBuilder decodeStringMessage = new StringBuilder(Messages.DECODE);
        String str = enterString.get();
        try {
            decodeResult.set(huffman.encodeString(str));
            decodeStringMessage.append(str).append(" " + Status.STRING_DECODED.toString());
            status.set(Status.STRING_DECODED.toString());
        } catch (IllegalArgumentException e) {
            decodeStringMessage.append(str).append(" " + Status.STRING_DECODED_FAILED.toString());
            status.set(Status.BAD_FORMAT.toString());
        }
        logcreator.log(decodeStringMessage.toString());
        updateLogs();
    }

    public void encodeString() {
        StringBuilder encodeStringMessage = new StringBuilder(Messages.ENCODE);
        String str = enterString.get();
        try {
            encodeResult.set(huffman.decodeBinarySequenceByCurrentHTree(str));
            encodeStringMessage.append(str).append(" " + Status.STRING_ENCODED.toString());
            status.set(Status.STRING_ENCODED.toString());
        } catch (IllegalArgumentException e) {
            encodeStringMessage.append(str).append(" " + Status.STRING_ENCODED_FAILED.toString());
            status.set(Status.BAD_FORMAT.toString());
        }
        logcreator.log(encodeStringMessage.toString());
        updateLogs();
    }

    private void updateLogs() {
        List<String> allLogMessages = logcreator.getLog();
        String logMessage = new String();
        for (String log : allLogMessages) {
            logMessage += log + "\n";
        }
        logs.set(logMessage);
    }

    public final List<String> getLogCreator() {
        return logcreator.getLog();
    }
}

enum Status {
    WAITING("Please provide input data"),
    BAD_FORMAT("Bad format!"),
    STRING_DECODED("Successfully decoded!"),
    STRING_ENCODED("Successfully encoded!"),
    STRING_DECODED_FAILED("Failed decoded!"),
    STRING_ENCODED_FAILED("Failed encoded!");

    private final String status;

    Status(final String status) {
        this.status = status;
    }

    public String toString() {
        return this.status;
    }
}

final class Messages {
    public static final String DECODE = "Decode string: ";
    public static final String ENCODE = "Encode string: ";

    private Messages() {
    }
}
