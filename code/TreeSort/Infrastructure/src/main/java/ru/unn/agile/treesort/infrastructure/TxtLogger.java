package ru.unn.agile.treesort.infrastructure;

import ru.unn.agile.treesort.viewmodel.ILogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private static final String FILENAME = "./log";
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final String filename;
    private final File file;
    private final Path path;

    public TxtLogger(final String filename) {
        this.filename = filename;
        this.file = new File(filename);
        this.path = file.toPath();
    }

    public TxtLogger() {
        this(FILENAME);
    }

    public String getFileName() {
        return filename;
    }

    private String getTimestamp() {
        return new SimpleDateFormat(TIMESTAMP_FORMAT, Locale.ENGLISH)
                .format(Calendar.getInstance().getTime());
    }

    @Override
    public void log(final String text) {
        if (!file.exists()) {
            try {

                Files.createFile(path);
            } catch (IOException e) {
                // do nothing
            }
        }
        if (file.isFile()) {
            try {
                FileWriter fileWriter = new FileWriter(filename, true);
                fileWriter.append(
                        getTimestamp() + " > " + text + System.getProperty("line.separator"));
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }

    @Override
    public List<String> getLog() {
        List<String> logList = new ArrayList<>();
        if (file.isFile()) {
            try {
                logList = Files.readAllLines(path);
            } catch (IOException e) {
                // do nothing
            }
        }
        return logList;
    }
}
