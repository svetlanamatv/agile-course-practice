package ru.unn.agile.MassConverter.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private ArrayList<String> log = new ArrayList<String>();

    @Override
    public void log(final String s) {
        log.add(s);
    }

    @Override
    public List<String> getLog() {
        return log;
    }

    @Override
    public String getLastMessage() {
        String lastMessage;
        if (log.isEmpty()) {
            lastMessage = "";
        } else {
            lastMessage = log.get(log.size() - 1);
        }
        return lastMessage;
    }
}
