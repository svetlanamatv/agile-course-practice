package ru.unn.agile.triangle.logging;
import java.time.LocalDateTime;

public class LoggerRecord {
    private final String message;
    private final LocalDateTime timestamp;

    public LoggerRecord(final String message,
                        final LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public final String getMessage() {
        return message;
    }

    public final LocalDateTime getTimestamp() {
        return timestamp;
    }
}
