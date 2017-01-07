package ru.unn.agile.Statistics.infrastructure;

import ru.unn.agile.Statistics.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final String filename;

    private static String now() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

    public TxtLogger(final String filename) throws Exception {
        this.filename = filename;

        BufferedWriter logWriter = null;
        FileWriter fileWriter = new FileWriter(filename);
        logWriter = new BufferedWriter(fileWriter);

        writer = logWriter;
    }

    @Override
    public List<String> getLog() {
        ArrayList<String> log = new ArrayList<String>();

        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader reader = new BufferedReader(fileReader);

            String msg = reader.readLine();
            while (msg != null) {
                log.add(msg);
                msg = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

    @Override
    public void log(final String message) {
        try {
            writer.write(now() + " > " + message);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
