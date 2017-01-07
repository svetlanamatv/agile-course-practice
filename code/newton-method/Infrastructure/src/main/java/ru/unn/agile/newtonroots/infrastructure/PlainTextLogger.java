package ru.unn.agile.newtonroots.infrastructure;

import ru.unn.agile.newtonroots.viewmodel.Logger;
import ru.unn.agile.newtonroots.viewmodel.TimestampingInMemoryLogger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class PlainTextLogger implements Logger {
    private final PrintWriter logFileWriter;
    private final TimestampingInMemoryLogger memoryBackedLogger = new TimestampingInMemoryLogger();

    public PlainTextLogger(final String filename) throws IOException {
        logFileWriter = new PrintWriter(new FileWriter(filename), true);
    }

    @Override
    public List<String> getMessageList() {
        return memoryBackedLogger.getMessageList();
    }

    @Override
    public String getLastMessage() {
        return memoryBackedLogger.getLastMessage();
    }

    @Override
    public void appendMessage(final String message) {
        memoryBackedLogger.appendMessage(message);
        logFileWriter.println(memoryBackedLogger.getLastMessage());
    }

    @Override
    public int getMessageCount() {
        return memoryBackedLogger.getMessageCount();
    }
}
