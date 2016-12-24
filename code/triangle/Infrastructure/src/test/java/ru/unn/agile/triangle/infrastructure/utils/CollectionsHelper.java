package ru.unn.agile.triangle.infrastructure.utils;

import javafx.util.Pair;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CollectionsHelper {
    public static <A, B> List<Pair<A, B>> zip(final List<A> as, final List<B> bs) {
        return IntStream.range(0, Math.min(as.size(), bs.size()))
                .mapToObj(i -> new Pair<>(as.get(i), bs.get(i)))
                .collect(Collectors.toList());
    }

    private CollectionsHelper() {
    }
}
