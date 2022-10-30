package com.pasich.mynotes.data.preferences;


import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_SORT_PREF;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_TEXT_SIZE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_TEXT_STYLE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_SORT;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_SIZE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_STYLE;

import com.pasich.mynotes.utils.constants.PreferencesConfig;
import com.preference.PowerPreference;
import com.preference.Preference;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class AppPreferencesHelper implements PreferenceHelper {

    @Inject
    AppPreferencesHelper() {
    }

    @Override
    public Preference getDefaultPreferences() {
        return PowerPreference.getDefaultFile();
    }

    @Override
    public int getFormatCount() {
        return getDefaultPreferences().getInt(PreferencesConfig.ARGUMENT_PREFERENCE_FORMAT, PreferencesConfig.ARGUMENT_DEFAULT_FORMAT_VALUE);
    }

    @Override
    public int getSizeTextNoteActivity() {
        return getDefaultPreferences().getInt(ARGUMENT_PREFERENCE_TEXT_SIZE, ARGUMENT_DEFAULT_TEXT_SIZE);
    }

    @Override
    public String getSortParam() {
        return getDefaultPreferences().getString(ARGUMENT_PREFERENCE_SORT, ARGUMENT_DEFAULT_SORT_PREF);
    }

    @Override
    public String getTypeFaceNoteActivity() {
        return getDefaultPreferences().getString(ARGUMENT_PREFERENCE_TEXT_STYLE, ARGUMENT_DEFAULT_TEXT_STYLE);
    }


}
