package ru.unn.agile.PomodoroManager.viewmodel;
import java.util.List;
import java.util.ArrayList;

public class FakeLogger implements ILogger {
    @Override
    public List<String> getLog() {
        return log;
    }

    @Override
    public void log(final String s) {
        log.add(s);
    }

    private ArrayList<String> log = new ArrayList<String>();
}
