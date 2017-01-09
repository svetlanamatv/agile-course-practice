package unn.agile.SolvingQuadraticEquation.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class FileLoggerTests {
    private Pattern messageLogPattern;
    private Matcher messageLogMatcher;
    private static final String FILE_NAME_FOR_LOG =
            "./TxtLogger_Tests-quadraticEquationSolver.log";
    private FileQuadraticEquationSolverLogger fileLogger;

    @Before
    public void setUp() {
        fileLogger = new FileQuadraticEquationSolverLogger(FILE_NAME_FOR_LOG);
    }

    @Test
    public void makeLoggerWithFileName() {
        assertNotNull(fileLogger);
    }

    @Test
    public void makeFileOnDiskForLog() {
        try {
            new BufferedReader(new FileReader(FILE_NAME_FOR_LOG));
        } catch (FileNotFoundException e) {
            fail("File " + FILE_NAME_FOR_LOG + " wasn't found!");
        }
    }

    @Test
    public void writeOneLogMessage() {
        String testMessage = "message";

        fileLogger.makeLog(testMessage);

        String messageFromFile = fileLogger.getLog().get(0);
        messageLogPattern = Pattern.compile(".*" + testMessage + "$");
        messageLogMatcher = messageLogPattern.matcher(messageFromFile);
        assertTrue(messageLogMatcher.matches());
    }

    @Test
    public void writeSeveralLogMessages() {
        String[] messagesForTest = {"message 1", "message 2", "message 3"};

        fileLogger.makeLog(messagesForTest[0]);
        fileLogger.makeLog(messagesForTest[1]);
        fileLogger.makeLog(messagesForTest[2]);

        List<String> actualMessages = fileLogger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            messageLogPattern = Pattern.compile(".*" + messagesForTest[i] + "$");
            messageLogMatcher = messageLogPattern.matcher(actualMessages.get(i));
            assertTrue(messageLogMatcher.matches());
        }
    }

    @Test
    public void checkLogContainDateAndTime() {
        String messageForTest = "message";

        fileLogger.makeLog(messageForTest);

        String message = fileLogger.getLog().get(0);
        messageLogPattern =
                Pattern.compile("^\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\] > .*");
        messageLogMatcher = messageLogPattern.matcher(message);
        assertTrue(messageLogMatcher.matches());
    }
}
