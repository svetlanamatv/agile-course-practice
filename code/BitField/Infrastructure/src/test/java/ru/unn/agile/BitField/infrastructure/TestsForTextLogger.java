package ru.unn.agile.BitField.infrastructure;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TestsForTextLogger {
    private static final String FILENAME = "./TxtLogger_Tests-lab3.log";
    private TextLog textingLog;

    @Before
    public void setOn() {
        textingLog = new TextLog(FILENAME);
    }

    @Test
    public void creatingLogWithNameofFile() {
        assertNotNull(textingLog);
    }


    @Test
    public void canCreateDiskLogger() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " not found!");
        }
    }
}
