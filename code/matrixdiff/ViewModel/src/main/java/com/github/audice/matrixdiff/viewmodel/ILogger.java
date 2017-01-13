package com.github.audice.matrixdiff.viewmodel;

import java.util.List;

/**
 * Created by Denis on 09.01.2017.
 */
public interface ILogger {
    void log(String s);

    List<String> getLog();
}
