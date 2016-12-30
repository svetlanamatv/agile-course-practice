package ru.unn.agile.newtonroots.viewmodel.testutils;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RegexMatcher extends TypeSafeMatcher<String> {
    private final String pattern;

    private RegexMatcher(final String pattern) {
        this.pattern = pattern;
    }

    @Override
    protected boolean matchesSafely(final String item) {
        return item.matches(pattern);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("string matching pattern=`" + pattern + "`");
    }

    public static RegexMatcher matchesPattern(final String pattern) {
        return new RegexMatcher(pattern);
    }
}
