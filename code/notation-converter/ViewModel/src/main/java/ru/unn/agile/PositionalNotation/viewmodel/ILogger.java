package ru.unn.agile.PositionalNotation.viewmodel;

import java.util.List;

/**
 * Created by a.tarasenko on 21.12.2016.
 */
public interface ILogger {
    void writeLog(String s);

    List<String> readLogs();
}
