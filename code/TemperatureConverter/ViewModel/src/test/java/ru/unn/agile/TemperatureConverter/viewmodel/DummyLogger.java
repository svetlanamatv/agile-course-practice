package ru.unn.agile.TemperatureConverter.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jane on 12.12.2016.
 */
public class DummyLogger implements ILogger {
    private final ArrayList<String> currentLog = new ArrayList<>();
    @Override
    public void log(final String msg) {
        currentLog.add(msg);
    }

    @Override
    public List<String> getLog() {
        return currentLog;
    }
}
