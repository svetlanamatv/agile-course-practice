package ru.unn.agile.MyDeque.viewmodel;

import ru.unn.agile.MyDeque.Model.Deque;

import static java.lang.Integer.parseInt;
import static ru.unn.agile.MyDeque.viewmodel.ViewModel.Status.*;
import static ru.unn.agile.MyDeque.viewmodel.ViewModel.Operations.*;
import static ru.unn.agile.MyDeque.viewmodel.KeyboardsKeys.*;

public class ViewModel {
    private String value;
    private String result;
    private String status;
    private Operations operation;
    private boolean isAcceptButtonEnabled;
    private final Deque deque = new Deque();

    public ViewModel() {
        value = "";
        status = WAITING;
        result = "";
        operation = PUSH_HEAD;
        isAcceptButtonEnabled = false;
    }

    public String getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }

    public void setValue(final String value) {
        if (value.equals(this.value)) {
            return;
        }

        this.value = value;
    }

    public void textFieldKey(final int codeKey) {
        parseInput();

        if (codeKey == ENTER) {
            enterPressed();
        }
    }

    private void enterPressed() {
        if (isAcceptButtonEnabled()) {
            accept();
        }
    }

    public void accept() {
        if (!parseInput()) {
            return;
        }

        switch (operation) {
            case PUSH_HEAD:
                deque.pushHeadElement(parseInt(value));
                result = deque.toString();
                break;
            case PUSH_TAIL:
                deque.pushTailElement(parseInt(value));
                result = deque.toString();
                break;
            case POP_HEAD:
                result = Integer.toString(deque.popHeadElement());
                break;
            case POP_TAIL:
                result = Integer.toString(deque.popTailElement());
                break;
            default:
                throw new IllegalArgumentException(
                        "Only can only use push & pop operations for head & tail elements");
        }

        status = SUCCESS;
    }

    public String getResult() {
        return result;
    }

    public boolean isAcceptButtonEnabled() {
        return isAcceptButtonEnabled;
    }

    private boolean parseInput() {
        try {
            if (!value.isEmpty()) {
                parseInt(value);
            }
        } catch (Exception e) {
            status = BAD_FORMAT;
            isAcceptButtonEnabled = false;
            return false;
        }

        isAcceptButtonEnabled = isInputAvailable();
        if (isAcceptButtonEnabled) {
            status = READY;
        } else {
            status = WAITING;
        }

        return isAcceptButtonEnabled;
    }

    public Operations getOperation() {
        return operation;
    }

    public void setOperation(final Operations operation) {
        this.operation = operation;
    }

    private boolean isInputAvailable() {
        return !value.isEmpty();
    }

    public enum Operations {
        PUSH_HEAD("Push head"),
        POP_HEAD("Pop head"),
        PUSH_TAIL("Push tail"),
        POP_TAIL("Pop tail");
        private final String operateName;

        Operations(final String operateName) {
            this.operateName = operateName;
        }

        public String toString() {
            return operateName;
        }
    }

    public final class Status {
        public static final String WAITING = "Please provide input data";
        public static final String READY = "Press 'Calculate' or Enter";
        public static final String BAD_FORMAT = "Bad format";
        public static final String SUCCESS = "Success";

        private Status() {
        }
    }
}
