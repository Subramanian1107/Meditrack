package main.java.com.airtribe.meditrack.util;

import java.util.*;

public class DataStore<T> {

    private Map<Integer, T> store = new HashMap<>();

    public void add(int id, T obj) {
        store.put(id, obj);
    }

    public T get(int id) {
        return store.get(id);
    }

    public List<T> getAll() {
        return new ArrayList<>(store.values());
    }

    public void remove(int id) {
        store.remove(id);
    }
}