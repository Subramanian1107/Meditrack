package main.java.com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

    private static final IdGenerator instance = new IdGenerator();
    private AtomicInteger counter = new AtomicInteger(1);

    private IdGenerator() {}

    public static IdGenerator getInstance() {
        return instance;
    }

    public int generateId() {
        return counter.getAndIncrement();
    }
}