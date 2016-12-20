package ru.unn.agile.TemperatureConverter.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.*;

/**
 * Created by Jane on 18.12.2016.
 */
public class TxtLoggerTests {
    private static final String FILE = "./TestTxtLogger.log";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILE);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }
    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(FILE));
        } catch (FileNotFoundException e) {
            fail("File " + FILE + " not found!");
        }
    }
    @Test
    public void doesLogIncludeDate() {
        txtLogger.log("This is date test");
        String msg = txtLogger.getLog().get(0);
        assertTrue(msg.matches("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2} >> .*"));
    }
    @Test
    public void canWriteLog() {
        txtLogger.log("This is test");
        String msg = txtLogger.getLog().get(0);
        assertTrue(msg.matches(".*This is test*"));
    }
    @Test
    public void canWriteThreeLogs() {
        txtLogger.log("First");
        txtLogger.log("Second");
        txtLogger.log("Fird");
        assertEquals(3, txtLogger.getLog().size());
    }
}
