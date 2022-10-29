package com.pasich.mynotes.utils.permissionManager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.pasich.mynotes.di.scope.ActivityContext;

import javax.inject.Inject;

public class PermissionManager {

    private final Context context;

    @Inject
    public PermissionManager(@ActivityContext Context context) {
        this.context = context;
    }

    /**
     * Запросить разрешение на использование микрофона
     */
    public boolean checkPermissionVoice() {
        AudioPermission audioPermission = (AudioPermission) context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                audioPermission.openShouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO);
                audioPermission.openRequestPermissions(new String[]{Manifest.permission.RECORD_AUDIO});
                return false;
            }
        } else {
            return true;
        }
    }
}
