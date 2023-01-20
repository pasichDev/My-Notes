package com.pasich.mynotes.utils;

import android.accounts.Account;

import javax.inject.Inject;

public class BackupServiceCache {

    private Account account;
    private boolean isHasPermissionDrive;
    private String jsonBackup;

    @Inject
    public BackupServiceCache() {

    }


    public BackupServiceCache build(Account account, boolean isHasPermissionDrive) {
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

    public String getJsonBackup() {
        return jsonBackup;
    }

    public void setJsonBackup(String jsonBackup) {
        this.jsonBackup = jsonBackup;
    }
}
