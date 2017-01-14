package ru.unn.agile.PomodoroManager.viewmodel;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RgxMatcher extends BaseMatcher {
    private final String regexString;

    public RgxMatcher(final String regexInit) {
        this.regexString = regexInit;
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(regexString);
    }

    public void describeTo(final Description description) {
        description.appendText("matches regex = ");
        description.appendText(regexString);
    }

    public static Matcher<? super String> matchesPattern(final String regex) {
        RgxMatcher matcher = new RgxMatcher(regex);
        //NOTE: this ugly cast is needed to workaround 'unchecked' Java warning
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }
}
