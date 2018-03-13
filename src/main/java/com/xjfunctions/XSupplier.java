package com.xjfunctions;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Represents a supplier of results that might throw any exception.
 *
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 *
 * <p>This is a functional interface
 * whose functional method is {@link #get()}.
 *
 * @param <T> the type of results supplied by this supplier
 *
 * @since XJFunction 1.0
 * @author Victor Williams Stafusa da Silva
 */
@FunctionalInterface
public interface XSupplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws Throwable the exception that might be propagated
     */
    public T get() throws Throwable;

    /**
     * Unwraps this object into a standard {@link Supplier}.
     * Any exception which might be thrown by the returned supplier
     * will be wrapped up into an {@link WrapperException}.
     * @return An unwrapped standard {@link Supplier}
     */
    public default Supplier<T> unchecked() {
        return () -> {
            try {
                return get();
            } catch (Throwable x) {
                throw new WrapperException(x);
            }
        };
    }

    /**
     * Wraps a standard {@link Supplier} into a {@code XSupplier}.
     * @param toWrap the {@link Supplier} which will be wrapped
     * @param <T> the type of output of the {@code toWrap} supplier
     * @return toWrap wrapped as a {@code XSupplier}.
     * @throws NullPointerException if toWrap is null
     */
    public static <T> XSupplier<T> wrap(Supplier<T> toWrap) {
        Objects.requireNonNull(toWrap, "toWrap");
        return toWrap::get;
    }
}
