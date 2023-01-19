package com.pasich.mynotes.utils.constants;

import com.google.android.gms.common.api.Scope;
import com.google.api.services.drive.DriveScopes;

public class Drive_Scope {
    public static Scope ACCESS_DRIVE_SCOPE = new Scope(DriveScopes.DRIVE_APPDATA);
    public static String APPLICATION_NAME = "My Notes";
}
