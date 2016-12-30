package ru.unn.agile.todoapp.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PlainTextLoggerTests {
    private static final String TEST_MESSAGE = "test message";
    private static final String FILENAME = "./testLog.log";
    private PlainTextLogger textLogger;

    @Before
    public void setUp() {
        try  {
            textLogger = new PlainTextLogger(FILENAME);
        } catch (IOException e)  {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void loggerWasCreated()  {
        assertNotNull(textLogger);
    }

    @Test
    public void logFileWasCreated()  {
        File logFile = new File(FILENAME);

        assertTrue(logFile.exists());
    }

    @Test
    public void canAddOneLogMessage()  {
        textLogger.addToLog(TEST_MESSAGE);
        String lastMessage = textLogger.getLastLogMessage();

        assertTrue(lastMessage.matches(".*" + TEST_MESSAGE));
    }

    @Test
    public void canAddTwoLogMessages()  {
        textLogger.addToLog(TEST_MESSAGE);
        textLogger.addToLog(TEST_MESSAGE + "2");

        List<String> log = textLogger.getLog();

        assertEquals(2, log.size());
    }

    @Test
    public void logContainsDateAndTime()  {
        textLogger.addToLog(TEST_MESSAGE);
        String lastMessage = textLogger.getLastLogMessage();

        assertTrue(lastMessage.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }

    @Test
    public void canRunOnLogUpdateAction()  {
        LoggerOnLogUpdateTestHelper testHelper = new LoggerOnLogUpdateTestHelper();
        textLogger.setOnLogUpdateAction(testHelper);
        textLogger.addToLog(TEST_MESSAGE);

        assertTrue(testHelper.wasCalled());
    }
}

class LoggerOnLogUpdateTestHelper implements Runnable {
    private boolean called = false;

    @Override
    public void run() {
        called = true;
    }

    public boolean wasCalled() {
        return called;
    }
}
