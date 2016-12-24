package ru.unn.agile.queue.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegularExpressionMatcher extends BaseMatcher {
    private final String regularExpression;

    public RegularExpressionMatcher(final String regularExpression) {
        this.regularExpression = regularExpression;
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(regularExpression);
    }

    public void describeTo(final Description description) {
        description.appendText("matches regularExpression = ").appendText(regularExpression);
    }

    public static Matcher<? super String> matchesPattern(final String regex) {
        RegularExpressionMatcher matcher = new RegularExpressionMatcher(regex);
        //NOTE: this ugly cast is needed to workaround 'unchecked' Java warning
        @SuppressWarnings(value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>) matcher;
        return castedMatcher;
    }
}
