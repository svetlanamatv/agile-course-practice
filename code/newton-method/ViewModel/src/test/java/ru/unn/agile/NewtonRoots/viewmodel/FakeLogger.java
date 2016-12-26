package ru.unn.agile.NewtonRoots.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements Logger {
    private final ArrayList<String> messageList = new ArrayList<>();

    @Override
    public List<String> getLog() {
        return messageList;
    }

    @Override
    public void appendMessage(String message) {
        messageList.add(message);
    }
}
