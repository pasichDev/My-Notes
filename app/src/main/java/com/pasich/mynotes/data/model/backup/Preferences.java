package com.pasich.mynotes.data.model.backup;

public class Preferences<T> {

    private final String key;
    private final T value;

    public Preferences(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }
}
