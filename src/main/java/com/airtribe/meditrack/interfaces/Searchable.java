package com.airtribe.meditrack.interfaces;

import java.util.List;

public interface Searchable<T> {

    T searchById(int id);

    default void printAll(List<T> list) {
        list.forEach(System.out::println);
    }
}
