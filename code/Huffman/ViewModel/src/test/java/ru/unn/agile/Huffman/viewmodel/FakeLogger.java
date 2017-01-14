package ru.unn.agile.Huffman.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void log(final String logstr) {
        log.add(logstr);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}

