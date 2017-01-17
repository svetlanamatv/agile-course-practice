package ru.unn.agile.Salary.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LoggerTests {
    private static final String FILENAME = "logFile.log";
    private FileLogger fileLogger;

    @Before
    public void setUp() {
        fileLogger = new FileLogger(FILENAME);
    }

    @Test
    public void testCreateLogger() {
        assertNotNull(fileLogger);
    }

    @Test
    public void testCreateLogFile() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " not found!");
        }
    }

    @Test
    public void testWriteLogMessage() {
        String testMessage = "Test message";

        fileLogger.log(testMessage);

        String message = fileLogger.getLog().get(0);
        assertTrue(message.matches(".*" + testMessage + "$"));
    }

    @Test
    public void testLogContainsDateAndTime() {
        String testMessage = "Test message";

        fileLogger.log(testMessage);

        String message = fileLogger.getLog().get(0);
        assertTrue(message.matches("^\\* \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} \\*: .*"));
    }

    @Test(expected = Exception.class)
    public void testWriteMessage() {
        FileLogger failedLogger = new FileLogger(null);
        String testMessage = "Test message";

        failedLogger.log(testMessage);

        String message = failedLogger.getLog().get(0);
        assertTrue(message.matches(".*" + testMessage + "$"));
    }
}
