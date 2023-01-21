package com.pasich.mynotes.data.model;

import com.pasich.mynotes.utils.constants.Cloud_Error;

public class LastBackupCloud {

    private final String id;
    private final long lastData;
    private final int errorCode;

    public LastBackupCloud(String id, long lastData) {
        this.lastData = lastData;
        this.id = id;
        this.errorCode = Cloud_Error.NO_ERROR;
    }

    public LastBackupCloud(int errorCode) {
        this.lastData = 0;
        this.id = "null";
        this.errorCode = errorCode;
    }

    public long getLastDate() {
        return lastData;
    }

    public String getId() {
        return id;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
