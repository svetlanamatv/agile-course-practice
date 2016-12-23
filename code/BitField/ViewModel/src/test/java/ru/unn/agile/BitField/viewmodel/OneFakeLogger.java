package ru.unn.agile.BitField.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class OneFakeLogger implements TheLog {
    private final ArrayList<String> thisLog = new ArrayList<>();

    @Override
    public void log(final String s) {
        thisLog.add(s);
    }

    @Override
    public List<String> getLog() {
        return thisLog;
    }
}
