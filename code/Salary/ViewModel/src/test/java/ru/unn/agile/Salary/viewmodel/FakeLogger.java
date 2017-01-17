package ru.unn.agile.Salary.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private final ArrayList<String> logArray = new ArrayList<>();

    @Override
    public void log(final String str) {
        logArray.add(str);
    }

    @Override
    public List<String> getLog() {
        return logArray;
    }
}
