package ru.unn.agile.triangle.viewmodel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.triangle.logging.LoggerRecord;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class LoggerRecordViewModelTests {
    private static final String LOGGER_MESSAGE = "Logger message";
    private static final LocalDateTime LOGGER_TIMESTAMP = LocalDateTime.now();
    private LoggerRecord loggerRecord;

    @Before
    public void setUp() throws Exception {
        loggerRecord = new LoggerRecord(LOGGER_MESSAGE, LOGGER_TIMESTAMP);
    }

    @Test
    public void constructAndMessageCopiedFromLoggerRecord() throws Exception {
        LoggerRecordViewModel loggerRecordViewModel =
                new LoggerRecordViewModel(loggerRecord);

        assertEquals(LOGGER_MESSAGE, loggerRecordViewModel.getMessage());
    }

    @Test
    public void constructAndTimestampCopiedFromLoggerRecord() throws Exception {
        LoggerRecordViewModel loggerRecordViewModel =
                new LoggerRecordViewModel(loggerRecord);

        assertEquals(LOGGER_TIMESTAMP, loggerRecordViewModel.getTimestamp());
    }

    @Test(expected = NullPointerException.class)
    public void constructionFailsIfLoggerRecordIsNull() throws Exception {
        new LoggerRecordViewModel(null);
    }
}
