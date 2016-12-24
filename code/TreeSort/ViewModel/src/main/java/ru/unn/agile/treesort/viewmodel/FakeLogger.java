package ru.unn.agile.treesort.viewmodel;

import java.util.List;

public class FakeLogger implements ILogger {
    @Override
    public void log(final String text) {
        // do nothing;
    }

    @Override
    public List<String> getLog() {
        return null;
    }
}
