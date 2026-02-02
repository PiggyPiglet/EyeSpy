package com.jarhax.eyespy.impl.util;

import java.util.function.Supplier;

public class Util {
    public static <T> T make(ExceptionalSupplier<T> func) {
        try {
            return func.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface ExceptionalSupplier<R> {
        R get() throws Exception;
    }

    public static <T> Supplier<T> memoize(final ExceptionalSupplier<T> func) {
        return new Supplier<>() {
            private T cache;

            @Override
            public T get() {
                if (cache == null) {
                    try {
                        cache = func.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                return cache;
            }

        };
    }
}