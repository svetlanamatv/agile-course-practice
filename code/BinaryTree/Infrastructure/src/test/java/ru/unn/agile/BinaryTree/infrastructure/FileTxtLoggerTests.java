package ru.unn.agile.BinaryTree.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FileTxtLoggerTests {
    private static final String FILENAME = "./treeFile.log";
    private FileTxtLogger txtFileLogger;

    @Before
    public void setUp() {
        txtFileLogger = new FileTxtLogger(FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtFileLogger);
    }

    @Test
    public void canCreateLogFile() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " not found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";

        txtFileLogger.log(testMessage);

        String message = txtFileLogger.getLog().get(0);
        assertTrue(message.matches(".*" + testMessage + "$"));
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        txtFileLogger.log(testMessage);

        String message = txtFileLogger.getLog().get(0);
        assertTrue(message.matches("^\\[ \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} \\]: .*"));
    }

    @Test(expected = Exception.class)
    public void logMessageCantWrite() {
        FileTxtLogger failedLogger = new FileTxtLogger(null);
        String testMessage = "Test message";

        failedLogger.log(testMessage);

        String message = failedLogger.getLog().get(0);
        assertTrue(message.matches(".*" + testMessage + "$"));
    }
}
