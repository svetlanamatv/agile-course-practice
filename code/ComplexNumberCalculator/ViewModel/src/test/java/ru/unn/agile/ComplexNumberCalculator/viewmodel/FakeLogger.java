package ru.unn.agile.ComplexNumberCalculator.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DW on 18.12.2016.
 */
public class FakeLogger implements ILogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void log(final String s) {
        log.add(s);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}

