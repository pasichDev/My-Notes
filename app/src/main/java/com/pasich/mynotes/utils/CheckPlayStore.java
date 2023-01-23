package com.pasich.mynotes.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * <a href="https://stackoverflow.com/a/69030731/20004107">URL</a>
 */
public class CheckPlayStore {

    private static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    public static boolean isPlayStoreInstalled(Context context) {
        boolean flag;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(GOOGLE_PLAY_STORE_PACKAGE, 0);
            flag = packageInfo.applicationInfo.enabled;
        } catch (PackageManager.NameNotFoundException exc) {
            flag = false;
        }
        return flag;
    }
}
