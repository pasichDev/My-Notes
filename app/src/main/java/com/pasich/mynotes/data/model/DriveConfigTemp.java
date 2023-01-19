package com.pasich.mynotes.data.model;

import android.accounts.Account;

public class DriveConfigTemp {

    private final Account account;
    private boolean isHasPermissionDrive;


    public DriveConfigTemp(Account account, boolean isHasPermissionDrive) {
        this.account = account;
        this.isHasPermissionDrive = isHasPermissionDrive;
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
}
