package ru.unn.agile.triangle.infrastructure;

import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.triangle.infrastructure.utils.CollectionsHelper;
import ru.unn.agile.triangle.infrastructure.utils.RandomStringGenerator;
import ru.unn.agile.triangle.logging.LoggerRecord;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class PlainTextFileLoggerTests {
    private static final int MAX_RECORDS_IN_MEMORY = 200;
    private static final String TEXT_LOGGER_FILE =
            "./test-plain-text-logger-output.txt";

    private PlainTextFileLogger logger;

    @Before
    public void setUp() throws Exception {
        logger = new PlainTextFileLogger(TEXT_LOGGER_FILE, MAX_RECORDS_IN_MEMORY);
    }

    @After
    public void tearDown() throws Exception {
        logger.close();
        new File(TEXT_LOGGER_FILE).delete();
    }

    @Test
    public void returnsNonNullListOfRecords() throws Exception {
        assertNotEquals(logger.getLastRecords(logger.getRecordsNumber()), null);
    }

    @Test
    public void returnsEmptyListOfRecordsIfLoggerHasJustBeenCreated() throws Exception {
        assertTrue(logger.getLastRecords(logger.getRecordsNumber()).isEmpty());
    }

    @Test
    public void numberOfRecordsEqualsToZeroIfLoggerHasJustBeenCreated() throws Exception {
        assertEquals(0, logger.getRecordsNumber());
    }

    @Test
    public void recordsMessagesEqualToMessagesWereWritten() throws Exception {
        List<String> messages = RandomStringGenerator.randomStrings(
                MAX_RECORDS_IN_MEMORY / 2);

        printMessagesToLogger(messages);

        List<LoggerRecord> records = logger.getLastRecords(MAX_RECORDS_IN_MEMORY);
        assertEquals(messages.size(), records.size());
        for (Pair<LoggerRecord, String> pair : CollectionsHelper.zip(records, messages)) {
            String recordMessage = pair.getKey().getMessage();
            String message = pair.getValue();
            assertEquals(recordMessage, message);
        }
    }

    @Test
    public void outputFileCreated() throws Exception {
        File logFile = new File(TEXT_LOGGER_FILE);
        assertTrue(logFile.exists());
    }

    @Test
    public void fileOutputMessagesEqualToMessagesWereWritten() throws Exception {
        List<String> messages = RandomStringGenerator.randomStrings(
                MAX_RECORDS_IN_MEMORY / 2);

        printMessagesToLogger(messages);

        compareLogFileContentWithRealMessages(messages);
    }

    @Test
    public void recordsNumberIsNotGreaterThanMaxRecords() throws Exception {
        List<String> messages = RandomStringGenerator.randomStrings(
                2 * MAX_RECORDS_IN_MEMORY);

        printMessagesToLogger(messages);

        assertEquals(MAX_RECORDS_IN_MEMORY, logger.getRecordsNumber());
    }

    @Test
    public void recordsNumberEqualsToNumberOfRecordsLoggerReturned() throws Exception {
        List<String> messages = RandomStringGenerator.randomStrings(
                MAX_RECORDS_IN_MEMORY / 2);

        printMessagesToLogger(messages);

        List<LoggerRecord> records = logger.getLastRecords(logger.getRecordsNumber());
        assertEquals(logger.getRecordsNumber(), records.size());
    }

    @Test
    public void doesLoggerAddNewRecordsToTheExistingFile() throws Exception {
        List<String> messages = RandomStringGenerator.randomStrings(2);

        logger.print(messages.get(0));
        logger.close();

        logger = new PlainTextFileLogger(TEXT_LOGGER_FILE, MAX_RECORDS_IN_MEMORY);
        logger.print(messages.get(1));

        compareLogFileContentWithRealMessages(messages);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failIfLoggerWasClosed() throws Exception {
        logger.close();

        logger.print("Some message");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failIfModifyLoggerRecords() throws Exception {
        logger.print("Some message");

        logger.getLastRecords(MAX_RECORDS_IN_MEMORY).remove(0);
    }

    private static List<String> readLoggerFile() throws IOException {
        FileInputStream logStream = new FileInputStream(TEXT_LOGGER_FILE);
        InputStreamReader reader = new InputStreamReader(logStream);
        BufferedReader buffReader = new BufferedReader(reader);
        LinkedList<String> resultList = new LinkedList<>();

        String line;
        while ((line = buffReader.readLine()) != null) {
            resultList.addLast(line);
        }
        buffReader.close();

        return resultList;
    }

    private void printMessagesToLogger(final List<String> messages) {
        for (String message : messages) {
            logger.print(message);
        }
    }

    private void compareLogFileContentWithRealMessages(final List<String> messages)
            throws Exception {
        List<String> logLines = readLoggerFile();
        assertEquals(messages.size(), logLines.size());
        for (Pair<String, String> pair : CollectionsHelper.zip(logLines, messages)) {
            String logLine = pair.getKey();
            String message = pair.getValue();
            assertTrue(logLine.contains(message));
        }
    }
}
