package com.pasich.mynotes.base.interfaces;


import androidx.annotation.NonNull;

public interface AudioPermission {
    void openShouldShowRequestPermissionRationale(String permission);

    void openRequestPermissions(@NonNull String[] permissions);

}
