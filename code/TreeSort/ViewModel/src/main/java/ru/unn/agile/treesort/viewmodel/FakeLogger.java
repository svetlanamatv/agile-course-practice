package ru.unn.agile.treesort.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private final List<String> log = new ArrayList<>();

    @Override
    public void log(final String text) {
        log.add(text);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
