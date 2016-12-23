package ru.unn.agile.BitField.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class Matcher extends BaseMatcher {
    private final String regex;

    public Matcher(final String regex) {
        this.regex = regex;
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(regex);
    }

    public void describeTo(final Description description) {
        description.appendText("matches regex = ");
        description.appendText(regex);
    }

    public static org.hamcrest.Matcher matchesPattern(final String regex) {
        Matcher matcher = new Matcher(regex);
        //NOTE: this ugly cast is needed to workaround 'unchecked' Java warning
        @SuppressWarnings(value = "unchecked")
        org.hamcrest.Matcher castedMatcher = (org.hamcrest.Matcher) matcher;
        return castedMatcher;
    }
}
