package ru.unn.agile.Salary.infrastructure;

import ru.unn.agile.Salary.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FileLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter bufferedFileWriter;
    private final String filename;

    private static String getCurTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    public FileLogger(final String logfile) {
        this.filename = logfile;

        BufferedWriter logDumper = null;
        try {
            logDumper = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        bufferedFileWriter = logDumper;
    }

    @Override
    public void log(final String strlog) {
        try {
            bufferedFileWriter.write("* " + getCurTime() + " *: " + strlog);
            bufferedFileWriter.newLine();
            bufferedFileWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader bufferedLogReader;
        ArrayList<String> logArray = new ArrayList<String>();
        try {
            bufferedLogReader = new BufferedReader(new FileReader(filename));
            String line = bufferedLogReader.readLine();

            while (line != null) {
                logArray.add(line);
                line = bufferedLogReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return logArray;
    }
}
