package com.pasich.mynotes.data.preferences;


import com.preference.Preference;


public interface PreferenceHelper {

    Preference getDefaultPreferences();

    int getFormatCount();

    String getTypeFaceNoteActivity();

    int getSizeTextNoteActivity();

    String getSortParam();

    void editSizeTextNoteActivity(int value);
}
