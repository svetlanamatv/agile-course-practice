package ru.unn.agile.newtonroots.viewmodel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static ru.unn.agile.newtonroots.viewmodel.testutils.RegexMatcher.matchesPattern;

public class TimestampingInMemoryLoggerTests {
    private static final String TIMESTAMP_PATTERN = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
    private TimestampingInMemoryLogger logger;
    private static final String LOG_MESSAGE = "log message";

    @Before
    public void setUp() {
         logger = new TimestampingInMemoryLogger();
    }

    @Test
    public void afterInstantiationTheLogIsEmpty() {
        assertTrue(logger.getMessageList().isEmpty());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenQueryingLastMessageWithoutAppendingNullIsReturned() {
        assertNull(logger.getLastMessage());
    }

    @Test
    public void whenAppendingMessageLogSizeIncreases() {
        logger.appendMessage(LOG_MESSAGE);

        assertEquals(1, logger.getMessageCount());
    }

    @Test
    public void whenAppendingMessageItIsPrefixedByATimestamp() {
        logger.appendMessage(LOG_MESSAGE);

        String actualMessage = logger.getLastMessage();
        assertThat(actualMessage, matchesPattern(TIMESTAMP_PATTERN + " > " + LOG_MESSAGE));
    }
}
