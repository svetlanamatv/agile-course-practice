package ru.unn.agile.BitField.infrastructure;

import ru.unn.agile.BitField.viewmodel.TheLog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TextLog implements TheLog {
    private static final String DATA = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final String name;

    private static String now() {
        Calendar myCal = Calendar.getInstance();
        SimpleDateFormat formatic = new SimpleDateFormat(DATA, Locale.ENGLISH);
        return formatic.format(myCal.getTime());
    }

    public TextLog(final String filename) {
        this.name = filename;

        BufferedWriter writerForLog = null;
        try {
            writerForLog = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = writerForLog;
    }

    @Override
    public void log(final String s) {
        try {
            writer.write(now() + " > " + s);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getThisLog() {
        BufferedReader reader;
        ArrayList<String> logger = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(name));
            String logline = reader.readLine();

            while (logline != null) {
                logger.add(logline);
                logline = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return logger;
    }

}
