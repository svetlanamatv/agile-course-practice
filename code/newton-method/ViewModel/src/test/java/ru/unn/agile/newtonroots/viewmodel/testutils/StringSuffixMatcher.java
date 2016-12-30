package ru.unn.agile.newtonroots.viewmodel.testutils;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class StringSuffixMatcher extends TypeSafeMatcher<String> {
    private final String suffix;

    private StringSuffixMatcher(final String suffix) {
        this.suffix = suffix;
    }

    @Override
    protected boolean matchesSafely(final String item) {
        return item.endsWith(suffix);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("string ending with `" + suffix + "`");
    }

    public static StringSuffixMatcher endsWith(final String suffix) {
        return new StringSuffixMatcher(suffix);
    }
}
