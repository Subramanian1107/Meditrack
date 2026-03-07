package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

    private static final IdGenerator instance = new IdGenerator();
    private final AtomicInteger counter = new AtomicInteger(1);

    private IdGenerator() {}

    public static IdGenerator getInstance() {
        return instance;
    }

    public int generateId() {
        return counter.getAndIncrement();
    }

    /** Ensure future IDs are greater than this value (used when loading persisted data). */
    public void ensureMinimumId(int minId) {
        counter.updateAndGet(c -> Math.max(c, minId + 1));
    }
}