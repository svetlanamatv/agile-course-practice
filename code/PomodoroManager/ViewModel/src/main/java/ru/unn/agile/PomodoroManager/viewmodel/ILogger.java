package ru.unn.agile.PomodoroManager.viewmodel;

import java.util.List;

public interface ILogger {
    List<String> getLog();

    void log(String s);
}

