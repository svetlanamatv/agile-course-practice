package ru.unn.agile.PositionalNotaition.infrastructure;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.PositioanalNotation.infrastructure.PositionalNotationLogger;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PositionalNotationLoggerTests {
    private static final String FILENAME = "./PositionalNotationLog.log";
    private PositionalNotationLogger positionalNotationLogger;

    @Before
    public void setUp() {
        positionalNotationLogger = new PositionalNotationLogger(FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(positionalNotationLogger);
    }

    @Test
    public void canCreateFileLogOnDisk() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException ex) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";
        positionalNotationLogger.writeLog(testMessage);
        String message = positionalNotationLogger.readLogs().get(0);
        assertTrue(message.matches(".*Test message.*"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"Test message 1", "Test message 2"};
        positionalNotationLogger.writeLog(messages[0]);
        positionalNotationLogger.writeLog(messages[1]);
        List<String> listLog = positionalNotationLogger.readLogs();

        assertTrue(listLog.get(0).matches(".*Test message 1.*"));
        assertTrue(listLog.get(1).matches(".*Test message 2.*"));
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";
        positionalNotationLogger.writeLog(testMessage);
        String message = positionalNotationLogger.readLogs().get(0);

        assertTrue(message.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
