package com.pasich.mynotes.utils.permissionManager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import javax.inject.Inject;

public class PermissionManager {
    @Inject
    public PermissionManager() {
    }

    /**
     * Запросить разрешение на использование микрофона
     */
    public boolean checkPermissionVoice(Context context) {
        boolean check = false;
        AudioPermission audioPermission = (AudioPermission) context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {

                check = true;
            } else {
                audioPermission.openShouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO);
                audioPermission.openRequestPermissions(new String[]{Manifest.permission.RECORD_AUDIO});
            }
        } else {
            check = true;
        }
        return check;
    }
}
