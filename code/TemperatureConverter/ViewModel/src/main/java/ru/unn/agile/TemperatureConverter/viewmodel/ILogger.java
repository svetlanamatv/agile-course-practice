package ru.unn.agile.TemperatureConverter.viewmodel;

import java.util.List;

/**
 * Created by Jane on 12.12.2016.
 */
public interface ILogger {
    void log(String str);
    List<String> getLog();
}
