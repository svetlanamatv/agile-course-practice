package ru.unn.agile.NewtonRoots.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements Logger {
    private final ArrayList<String> messageList = new ArrayList<>();

    @Override
    public List<String> getMessageList() {
        return messageList;
    }

    @Override
    public String getLastMessage() {
        if (messageList.isEmpty()) {
            return null;
        }

        return messageList.get(messageList.size() - 1);
    }

    @Override
    public void appendMessage(String message) {
        messageList.add(message);
    }
}
