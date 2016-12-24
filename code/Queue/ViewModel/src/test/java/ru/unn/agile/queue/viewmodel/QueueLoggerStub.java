package ru.unn.agile.queue.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 23.12.2016.
 */
class QueueLoggerStub implements QueueLogger {
    private final ArrayList<String> messages = new ArrayList<>();

    @Override
    public void log(final String s) {
        messages.add(s);
    }

    @Override
    public List<String> getLog() {
        return messages;
    }
}
