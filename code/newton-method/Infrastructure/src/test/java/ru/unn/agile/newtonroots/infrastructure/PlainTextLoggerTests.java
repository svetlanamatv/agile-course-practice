package ru.unn.agile.newtonroots.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PlainTextLoggerTests {
    private static final String LOG_FILENAME = "newton-method.log";
    private PlainTextLogger logger;

    @Before
    public void setUp() throws IOException {
        logger = new PlainTextLogger(LOG_FILENAME);
    }

    @After
    public void tearDown() {
        Path logPath = Paths.get(LOG_FILENAME);
        try {
            Files.deleteIfExists(logPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void plainTextLoggerCanBeInstantiated() throws IOException {
        assertNotNull(logger);
    }

    @Test
    public void uponInstantiationLogFileIsCreated() throws IOException {
        assertTrue(Files.exists(Paths.get(LOG_FILENAME)));
    }

    @Test
    public void messageIsAppendedToLogFile() throws IOException {
        String expectedMessage = "TEST MESSAGE";
        logger.appendMessage(expectedMessage);

        BufferedReader logReader = new BufferedReader(new FileReader(LOG_FILENAME));
        String actualMessage = logReader.readLine();
        assertEquals(expectedMessage, actualMessage);
    }
}
