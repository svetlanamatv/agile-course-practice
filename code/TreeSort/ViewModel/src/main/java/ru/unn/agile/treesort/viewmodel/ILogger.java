package ru.unn.agile.treesort.viewmodel;

import java.util.List;

public interface ILogger {
    void log(String text);
    List<String> getLog();
}
