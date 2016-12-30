package ru.unn.agile.newtonroots.viewmodel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimestampingInMemoryLogger implements Logger {
    private static final String TIMESTAMP_FORMAT = "YYYY-MM-dd HH:mm:ss";
    private final ArrayList<String> messageList = new ArrayList<>();

    @Override
    public List<String> getMessageList() {
        return messageList;
    }

    @Override
    public String getLastMessage() {
        if (messageList.isEmpty()) {
            throw new IndexOutOfBoundsException("Last message of empty log was queried");
        }

        return messageList.get(messageList.size() - 1);
    }

    @Override
    public void appendMessage(String message) {
        String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));
        messageList.add(timestamp + " > " + message);
    }
}
