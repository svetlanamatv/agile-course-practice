package ru.unn.agile.PositioanalNotation.infrastructure;

import ru.unn.agile.PositionalNotation.viewmodel.ILogger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PositionalNotationLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter logerWriter;
    private final String logFilename;

    private static String nowDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    public PositionalNotationLogger(final String filename) {
        this.logFilename = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        logerWriter = logWriter;
    }

    @Override
    public void writeLog(final String s) {
        try {
            logerWriter.write(nowDate() + " > " + s);
            logerWriter.newLine();
            logerWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> readLogs() {
        BufferedReader readerBuff;
        ArrayList<String> logs = new ArrayList<String>();
        try {
            readerBuff = new BufferedReader(new FileReader(logFilename));
            String readLine = readerBuff.readLine();

            while (readLine != null) {
                logs.add(readLine);
                readLine = readerBuff.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return logs;
    }
}
