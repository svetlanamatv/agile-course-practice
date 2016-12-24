package ru.unn.agile.queue.viewmodel;

import java.io.IOException;
import java.util.List;

/**
 * Created by Alexander on 23.12.2016.
 */
public interface QueueLogger {
    void log(String s) throws IOException;

    List<String> getLog() throws IOException;
}
