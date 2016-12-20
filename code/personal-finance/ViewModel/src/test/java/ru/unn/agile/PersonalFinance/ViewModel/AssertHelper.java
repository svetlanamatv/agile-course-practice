package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Assert;

import java.util.List;

final class AssertHelper {
    public static void assertContains(final List collection, final Object object) {
        Assert.assertTrue(checkContains(collection, object));
    }

    public static void assertNotContains(final List collection, final Object object) {
        Assert.assertFalse(checkContains(collection, object));
    }

    private static boolean checkContains(final List collection, final Object object) {
        return collection.contains(object);
    }

    private AssertHelper() {
    }
}
