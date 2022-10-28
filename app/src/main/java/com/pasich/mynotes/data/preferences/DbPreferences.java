package com.pasich.mynotes.data.preferences;


import com.preference.Preference;


public interface DbPreferences {

    Preference getDefaultPreferences();

    int getFormatCount();

    String getTypeFaceNoteActivity();

    int getSizeTextNoteActivity();

}
