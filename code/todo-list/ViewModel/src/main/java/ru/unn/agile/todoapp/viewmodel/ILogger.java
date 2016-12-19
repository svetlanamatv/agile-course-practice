package ru.unn.agile.todoapp.viewmodel;

import java.util.List;

public interface ILogger {
    void addToLog(String s);

    List<String> getLog();
    String getLastLogMessage();
}
