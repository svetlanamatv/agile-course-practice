package ru.unn.agile.treesort.infrastructure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TxtLoggerTests {
    private TxtLogger logger;

    @Before
    public void setUp() {
        logger = new TxtLogger();
    }

    @After
    public void tearDown() {
        File file = new File(logger.getFileName());
        if (file.isFile()) {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger = null;
    }

    @Test
    public void canCreateTxtLoggerWithFilenameParam() {
        logger = new TxtLogger("filename.log");

        assertNotNull(logger);
    }

    @Test
    public void canLogText() {
        logger.log("test log text");
    }

    @Test
    public void canGetLogs() {
        List<String> logList = logger.getLog();

        assertNotNull(logList);
    }

    @Test
    public void canGetFileName() {
        String filename = logger.getFileName();

        assertNotNull(filename);
    }

    @Test
    public void checkLoggingCorrect() {
        String text = "test log text";

        logger.log(text);
        String storedMessage = logger.getLog().get(0);

        assertTrue(storedMessage.endsWith(text));
    }

    @Test
    public void checkMultipleLoggingCorrect() {
        String[] testTexts = new String[]{"first", "second", "third"};

        for (String text : testTexts) {
            logger.log(text);
        }
        List<String> loggedTexts = logger.getLog();

        for (int i = 0; i < loggedTexts.size(); i++) {
            testTexts[i].endsWith(loggedTexts.get(i));
        }
    }

    @Test
    public void doesLogContainTimestamp() {
        String testMessage = "test message";

        logger.log(testMessage);
        String loggedMsg = logger.getLog().get(0);

        assertTrue(loggedMsg.matches(
                "2[0-2][0-9][0-9]-[0-1][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9].*"));
    }
}
