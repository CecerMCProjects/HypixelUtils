package com.cecer1.modframework.common.utils;

import java.util.concurrent.TimeUnit;

public class MethodCallTimer {
    private long lastTick = Long.MAX_VALUE;

    public MethodCallTimer() {
    }

    public long call() {
        long now = System.nanoTime();
        long diffNanos = now - lastTick;
        if(diffNanos < 0)
            return 0;
        return TimeUnit.NANOSECONDS.toMillis(diffNanos);
    }
}
