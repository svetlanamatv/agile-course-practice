package ru.unn.agile.queue.viewmodel;

import ru.unn.agile.queue.model.Queue;

import java.util.List;
import java.util.NoSuchElementException;

public class ViewModel<T> {

    private final Queue<T> queue;

    private T value;
    private String result;
    private QueueLogger logger;

    public ViewModel(final QueueLogger logger) {
        queue = new Queue<>();
        setLogger(logger);
    }

    public final void setLogger(final QueueLogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public ViewModel() {
        queue = new Queue<>();
    }

    public void add() {
        if (isEmptyValue()) {
            result = "Value is empty!";
            logger.log(LogMessageCliche.ADD_BUTTON_PRESSED + result);
            return;
        }
        queue.enqueue(value);
        result = "'" + value + "' was added successfully";
        logger.log(LogMessageCliche.ADD_BUTTON_PRESSED + result);
    }

    public void remove() {
        if (queue.isEmpty()) {
            result = "Queue is empty! You don't delete value from empty queue!";
            logger.log(LogMessageCliche.REMOVE_BUTTON_PRESSED + result);
            return;
        }
        if (isEmptyValue()) {
            result = "Value is empty!";
            logger.log(LogMessageCliche.REMOVE_BUTTON_PRESSED + result);
            return;
        }
        if (queue.remove(value)) {
            result = "'" + value + "' was removed successfully";
        } else {
            result = "Value '" + value + "' is absent in the queue!";
        }
        logger.log(LogMessageCliche.REMOVE_BUTTON_PRESSED + result);
    }

    private boolean isEmptyValue() {
        return value.toString().isEmpty();
    }

    public void search() {
        if (queue.isEmpty()) {
            result = "Queue is empty!";
            logger.log(LogMessageCliche.SEARCH_BUTTON_PRESSED + result);
            return;
        }
        if (isEmptyValue()) {
            result = "Value is empty!";
            logger.log(LogMessageCliche.SEARCH_BUTTON_PRESSED + result);
            return;
        }
        try {
            int position = queue.searchElement(value);
            result = "The position of '" + value + "' is " + position;
        } catch (NoSuchElementException nsex) {
            nsex.getCause();
            result = "The value '" + value + "' is not found in queue";
        } finally {
            logger.log(LogMessageCliche.SEARCH_BUTTON_PRESSED + result);
        }
    }

    public void getSize() {
        if (queue.isEmpty()) {
            result = "Queue is empty!";
        } else {
            result = "The size of queue is " + queue.getSize();
        }
        logger.log(LogMessageCliche.SIZE_BUTTON_PRESSED + result);
    }

    public T getValue() {
        return value;
    }

    public void setValue(final T value) {
        this.value = value;
    }

    public List<T> getQueue() {
        return queue.getQueueAsList();
    }

    public String getResult() {
        return result;
    }

    public String getLog() {
        List<String> logs = logger.getLog();
        StringBuilder output = new StringBuilder();
        for (String line : logs) {
            output.append(line).append("\n");
        }
        return output.toString();
    }

    public List<String> getLogMessages() {
        return logger.getLog();
    }

    final class LogMessageCliche {
        public static final String ADD_BUTTON_PRESSED = "'ADD' button was pressed. Result "
                + "message: ";
        public static final String REMOVE_BUTTON_PRESSED = "'REMOVE' button was pressed. Result "
                + "message: ";
        public static final String SEARCH_BUTTON_PRESSED = "'SEARCH' button was pressed. Result "
                + "message: ";
        public static final String SIZE_BUTTON_PRESSED = "'Get size' button was pressed. Result "
                + "message: ";

        private LogMessageCliche() {
        }
    }
}
