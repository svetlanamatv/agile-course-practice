package ru.unn.agile.BitField.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class Matcher extends BaseMatcher {
    private final String reg;

    public Matcher(final String regex) {
        this.reg = regex;
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(reg);
    }

    public void describeTo(final Description gle) {
        gle.appendText("regexMatches = ");
        gle.appendText(reg);
    }

    public static org.hamcrest.Matcher matchesPattern(final String firstRegex) {
        Matcher matcher = new Matcher(firstRegex);

        @SuppressWarnings(value = "unchecked")
        org.hamcrest.Matcher casted = (org.hamcrest.Matcher) matcher;
        return casted;
    }
}
