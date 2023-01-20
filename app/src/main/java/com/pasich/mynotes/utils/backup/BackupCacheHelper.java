package com.pasich.mynotes.utils.backup;

import android.accounts.Account;

import com.pasich.mynotes.data.model.JsonBackup;

import javax.inject.Inject;

public class BackupCacheHelper {

    private Account account;
    private boolean isHasPermissionDrive;
    private JsonBackup jsonBackup;

    @Inject
    public BackupCacheHelper() {

    }


    public BackupCacheHelper build(Account account, boolean isHasPermissionDrive) {
        this.account = account;
        this.isHasPermissionDrive = isHasPermissionDrive;
        return this;
    }

    public Account getAccount() {
        return account;
    }


    public boolean isHasPermissionDrive() {
        return isHasPermissionDrive;
    }

    public void setHasPermissionDrive(boolean hasPermissionDrive) {
        isHasPermissionDrive = hasPermissionDrive;
    }

    public JsonBackup getJsonBackup() {
        return jsonBackup;
    }

    public void setJsonBackup(JsonBackup jsonBackup) {
        this.jsonBackup = jsonBackup;
    }
}
