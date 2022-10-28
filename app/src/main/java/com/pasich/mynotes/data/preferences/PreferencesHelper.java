package com.pasich.mynotes.data.preferences;


import com.preference.PowerPreference;
import com.preference.Preference;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class PreferencesHelper extends PowerPreference {

    @Inject
    PreferencesHelper() {
    }

    public Preference getDefaultPreference() {
        return PowerPreference.getDefaultFile();
    }
}
