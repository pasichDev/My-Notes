package com.pasich.mynotes.utils.permissionManager;


import androidx.annotation.NonNull;

public interface AudioPermission {
    void openShouldShowRequestPermissionRationale(String permission);

    void openRequestPermissions(@NonNull String[] permissions);

}
