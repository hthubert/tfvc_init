package com.guige.tfvc;

import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A less abstract subclass of AbstractFuture. This can be used to optimize setFuture by ensuring
 * that {@link #get} calls exactly the implementation of {@link AbstractFuture#get}.
 */
abstract class TrustedFuture<V> extends AbstractFuture<V> {
    @CanIgnoreReturnValue
    @Override
    public final V get() throws InterruptedException, ExecutionException {
        return super.get();
    }

    @CanIgnoreReturnValue
    @Override
    public final V get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return super.get(timeout, unit);
    }

    @Override
    public final boolean isDone() {
        return super.isDone();
    }

    @Override
    public final boolean isCancelled() {
        return super.isCancelled();
    }

    @Override
    public final void addListener(Runnable listener, Executor executor) {
        super.addListener(listener, executor);
    }

    @CanIgnoreReturnValue
    @Override
    public final boolean cancel(boolean mayInterruptIfRunning) {
        return super.cancel(mayInterruptIfRunning);
    }
}

public final class SettableFuture<V> extends TrustedFuture<V> {
    public static <V> SettableFuture<V> create() {
        return new SettableFuture();
    }

    @CanIgnoreReturnValue
    public boolean set(V value) {
        return super.set(value);
    }

    @CanIgnoreReturnValue
    public boolean setException(Throwable throwable) {
        return super.setException(throwable);
    }

    @CanIgnoreReturnValue
    public boolean setFuture(ListenableFuture<? extends V> future) {
        return super.setFuture(future);
    }

    private SettableFuture() {
    }
}
