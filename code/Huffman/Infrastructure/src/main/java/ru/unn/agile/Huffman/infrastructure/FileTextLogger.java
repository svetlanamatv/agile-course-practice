package ru.unn.agile.Huffman.infrastructure;

import ru.unn.agile.Huffman.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FileTextLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter buffLogWriter;
    private final String filename;

    private static String currentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    public FileTextLogger(final String filename) {
        this.filename = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        buffLogWriter = logWriter;
    }

    @Override
    public void log(final String str) {
        try {
            buffLogWriter.write("< " + currentTime() + " >: " + str);
            buffLogWriter.newLine();
            buffLogWriter.flush();
        } catch (Exception log_exception) {
            System.out.println(log_exception.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader bufferedLogMessageReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            bufferedLogMessageReader = new BufferedReader(new FileReader(filename));
            String line = bufferedLogMessageReader.readLine();

            while (line != null) {
                log.add(line);
                line = bufferedLogMessageReader.readLine();
            }
        } catch (Exception log_exception) {
            System.out.println(log_exception.getMessage());
        }

        return log;
    }
}
