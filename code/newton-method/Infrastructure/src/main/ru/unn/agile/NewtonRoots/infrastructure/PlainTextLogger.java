package ru.unn.agile.NewtonRoots.infrastructure;

import ru.unn.agile.NewtonRoots.viewmodel.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class PlainTextLogger implements Logger {
    private PrintWriter logFileWriter;

    PlainTextLogger(String filename) throws IOException {
        logFileWriter = new PrintWriter(new FileWriter(filename), true);
    }

    @Override
    public List<String> getMessageList() {
        return null;
    }

    @Override
    public String getLastMessage() {
        return null;
    }

    @Override
    public void appendMessage(String message) {
        logFileWriter.println(message);
    }
}
