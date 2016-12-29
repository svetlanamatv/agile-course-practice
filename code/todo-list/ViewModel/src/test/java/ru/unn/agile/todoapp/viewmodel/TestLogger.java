package ru.unn.agile.todoapp.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class TestLogger implements ILogger {
    private final ArrayList<String> log = new ArrayList<>();
    private Runnable onLogUpdate;

    public TestLogger() {
        onLogUpdate = () -> { };
    }

    @Override
    public void addToLog(final String s) {
        log.add(s);
        onLogUpdate.run();
    }

    @Override
    public List<String> getLog() {
        return log;
    }

    @Override
    public String getLastLogMessage()  {
        return log.get(log.size() - 1);
    }

    @Override
    public void setOnLogUpdateAction(final Runnable onLogUpdate)  {
        this.onLogUpdate = onLogUpdate;
    }
}

