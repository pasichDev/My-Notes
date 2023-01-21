package com.pasich.mynotes.data.model;

public class BackupCloud {

    private final String id;
    private final long lastData;

    public BackupCloud(String id, long lastData) {
        this.lastData = lastData;
        this.id = id;
    }


    public long getLastDate() {
        return lastData;
    }

    public String getId() {
        return id;
    }
}
