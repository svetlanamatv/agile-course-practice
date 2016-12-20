package ru.unn.agile.PersonalFinance.ViewModel.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

public final class GregorianCalendarHelper {
    public static LocalDate convertToLocalDate(final GregorianCalendar calendar) {
        return calendar.toZonedDateTime().toLocalDate();
    }

    public static GregorianCalendar convertFromLocalDate(final LocalDate localDate) {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return GregorianCalendar.from(zonedDateTime);
    }

    public static boolean compareToLocalDate(final GregorianCalendar oneDate,
                                             final LocalDate otherDate) {
        LocalDate localOneDate = convertToLocalDate(oneDate);
        return localOneDate.isEqual(otherDate);
    }

    private GregorianCalendarHelper() {
    }
}
