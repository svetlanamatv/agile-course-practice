package ru.unn.agile.NewtonRoots.infrastructure;

import ru.unn.agile.NewtonRoots.viewmodel.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PlainTextLogger implements Logger {
    private PrintWriter logFileWriter;
    private ArrayList<String> messages = new ArrayList<>();

    PlainTextLogger(String filename) throws IOException {
        logFileWriter = new PrintWriter(new FileWriter(filename), true);
    }

    @Override
    public List<String> getMessageList() {
        return messages;
    }

    @Override
    public String getLastMessage() {
        if (messages.isEmpty()) {
            return null;
        } else {
            return messages.get(messages.size() - 1);
        }
    }

    @Override
    public void appendMessage(String message) {
        messages.add(message);
        logFileWriter.println(message);
    }
}
