package ru.unn.agile.Huffman.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.unn.agile.Huffman.HuffmanAlg;

public class HuffmanViewModel {
    private final StringProperty enterString = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty decodeResult = new SimpleStringProperty();
    private final HuffmanAlg huffman = new HuffmanAlg();
    private final StringProperty encodeResult = new SimpleStringProperty();

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

    public HuffmanViewModel() {
        enterString.set("");
        status.set((Status.WAITING.toString()));
    }

    public void decodeString() {
        try {
            String str = enterString.get();
            decodeResult.set(huffman.encodeString(str));
            status.set(Status.STRING_DECODED.toString());
        } catch (IllegalArgumentException e) {
            status.set(Status.BAD_FORMAT.toString());
        }
    }

    public void encodeString() {
        try {
            String str = enterString.get();
            encodeResult.set(huffman.decodeBinarySequenceByCurrentHTree(str));
            status.set(Status.STRING_ENCODED.toString());
        } catch (IllegalArgumentException e) {
            status.set(Status.BAD_FORMAT.toString());
        }
    }
}

enum Status {
    WAITING("Please provide input data"),
    BAD_FORMAT("Bad format!"),
    STRING_DECODED("String successfully decoded!"),
    STRING_ENCODED("String successfully encoded!");

    private final String status;

    Status(final String status) {
        this.status = status;
    }

    public String toString() {
        return this.status;
    }
}
