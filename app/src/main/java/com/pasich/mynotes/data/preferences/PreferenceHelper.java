package com.pasich.mynotes.data.preferences;


import com.pasich.mynotes.data.model.backup.Preferences;
import com.preference.Preference;

import java.util.List;


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

    List<Preferences> getListPreferences();

    void setListPreferences(List<Preferences> preferences);
}
