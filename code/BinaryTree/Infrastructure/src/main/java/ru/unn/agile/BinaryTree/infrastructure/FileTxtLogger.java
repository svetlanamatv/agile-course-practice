package ru.unn.agile.BinaryTree.infrastructure;

import ru.unn.agile.BinaryTree.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FileTxtLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter bufferedFileWriter;
    private final String filename;

    private static String currentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    public FileTxtLogger(final String filename) {
        this.filename = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        bufferedFileWriter = logWriter;
    }

    @Override
    public void log(final String str) {
        try {
            bufferedFileWriter.write("[ " + currentTime() + " ]: " + str);
            bufferedFileWriter.newLine();
            bufferedFileWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader bufferedLogReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            bufferedLogReader = new BufferedReader(new FileReader(filename));
            String line = bufferedLogReader.readLine();

            while (line != null) {
                log.add(line);
                line = bufferedLogReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }
}
