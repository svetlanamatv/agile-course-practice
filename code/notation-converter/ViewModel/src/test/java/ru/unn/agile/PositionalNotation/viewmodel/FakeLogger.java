package ru.unn.agile.PositionalNotation.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.tarasenko on 21.12.2016.
 */
public class FakeLogger implements ILogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void writeLog(final String s) {
        log.add(s);
    }

    @Override
    public List<String> readLogs() {
        return log;
    }
}
