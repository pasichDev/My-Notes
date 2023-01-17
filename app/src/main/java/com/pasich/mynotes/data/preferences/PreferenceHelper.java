package com.pasich.mynotes.data.preferences;


import com.preference.Preference;


public interface PreferenceHelper {

    Preference getDefaultPreferences();

    Preference getBackupCloudInfoPreference();

    int getFormatCount();

    String getTypeFaceNoteActivity();

    int getSizeTextNoteActivity();

    String getSortParam();

    long getLastDataBackupCloud();

    String getLastBackupCloudId();

    void editSizeTextNoteActivity(int value);

    int getSetCloudAuthBackup();
}
