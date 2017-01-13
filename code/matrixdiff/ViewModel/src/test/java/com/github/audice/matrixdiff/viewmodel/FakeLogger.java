package com.github.audice.matrixdiff.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 09.01.2017.
 */
public class FakeLogger implements ILogger {
    private ArrayList<String> logList = new ArrayList<String>();

    @Override
    public void log(final String s) {
        logList.add(s);
    }

    @Override
    public List<String> getLog() {
        return logList;
    }
}
