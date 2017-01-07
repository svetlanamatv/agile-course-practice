package ru.unn.agile.newtonroots.viewmodel;

import java.util.List;

public interface Logger {
    List<String> getMessageList();
    String getLastMessage();
    void appendMessage(String message);
    int getMessageCount();
}
