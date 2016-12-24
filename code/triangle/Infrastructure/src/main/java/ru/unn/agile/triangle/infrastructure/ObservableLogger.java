package ru.unn.agile.triangle.infrastructure;

import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.logging.LoggerRecord;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ObservableLogger implements Logger {
    private final List<Consumer<LoggerRecord>> listeners = new ArrayList<>();

    @Override
    public void print(final String message) {
        LoggerRecord record = new LoggerRecord(message, LocalDateTime.now());

        addRecord(record);
        fireListenersEvent(record);
    }

    @Override
    public void print(final String pattern, final Object... args) {
        print(MessageFormat.format(pattern, args));
    }

    @Override
    public void addListenerForNewRecord(final Consumer<LoggerRecord> listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    private void fireListenersEvent(final LoggerRecord record) {
        for (Consumer<LoggerRecord> listener : listeners) {
            listener.accept(record);
        }
    }

    protected abstract void addRecord(LoggerRecord record);
}
