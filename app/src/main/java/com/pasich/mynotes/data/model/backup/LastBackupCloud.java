package com.pasich.mynotes.data.model.backup;

import com.pasich.mynotes.utils.constants.Cloud_Error;

public class LastBackupCloud extends BackupCloud {

    private final int errorCode;

    public LastBackupCloud(String id, long lastData) {
        super(id, lastData);
        this.errorCode = Cloud_Error.NO_ERROR;
    }

    public LastBackupCloud(int errorCode) {
        super("null", 0);
        this.errorCode = errorCode;
    }


    public int getErrorCode() {
        return errorCode;
    }
}
