package ru.unn.agile.MassConverter.infrastructure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

public class TxtLoggerTests {
    private static final String FILENAME = "./TxtLoggerTests.log";
    private TxtLogger txtLogger;

    @Before
    public void start() {
        txtLogger = new TxtLogger(FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFile() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertTrue(message.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";

        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertTrue(message.matches(".*" + testMessage + ".*"));
    }

    @Test
    public void canWriteSeveralLogMessages() {
        String[] messages = {"First message", "Second message"};

        txtLogger.log(messages[0]);
        txtLogger.log(messages[1]);

        List<String> actualMessages = txtLogger.getLog();

        assertTrue(actualMessages.get(0).matches(".*" + messages[0] + ".*"));
        assertTrue(actualMessages.get(1).matches(".*" + messages[1] + ".*"));
    }

    @Test
    public void canReturnCorrectlyLastMessage() {
        String message = "Test message";

        txtLogger.log(message);

        String actualMessage = txtLogger.getLastMessage();

        assertTrue(actualMessage.matches(".*" + message + ".*"));
    }

    @Test
    public void canReturnCorrectlyLastMessageFromSeveralLogMessages() {
        String[] messages = {"First message", "Second message"};

        txtLogger.log(messages[0]);
        txtLogger.log(messages[1]);

        String actualMessage = txtLogger.getLastMessage();

        assertTrue(actualMessage.matches(".*" + messages[1] + ".*"));
    }

    @Test
    public void canReturnCorrectlyLastMessageWhenLogIsEmpty() {
        String actualMessage = txtLogger.getLastMessage();

        assertTrue(actualMessage.matches(""));
    }
}
