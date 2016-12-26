package ru.unn.agile.NewtonRoots.viewmodel;

import java.util.List;

public interface Logger {
    List<String> getMessageList();
    String getLastMessage();
    void appendMessage(String message);
}
