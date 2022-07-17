package com.pasich.mynotes.ui.view.fragments;

import android.os.Bundle;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.dialogs.settings.aboutAppDialog.AboutAppDialog;
import com.pasich.mynotes.ui.view.dialogs.settings.settingsAppDialog.aboutAppDialog.SettingsAppDialog;

public class FragmentSettings extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_prefences, rootKey);
        final Preference aboutApp = findPreference("aboutApp");
        final Preference settingsApp = findPreference("settingsApp");

        assert aboutApp != null;
        aboutApp.setSummary("My Notes (v" + BuildConfig.VERSION_NAME + ")");
        aboutApp.setOnPreferenceClickListener(preference -> {
            new AboutAppDialog().show(getParentFragmentManager(), "AboutApp");
            return true;
        });
        assert settingsApp != null;
        settingsApp.setOnPreferenceClickListener(preference -> {
            new SettingsAppDialog().show(getParentFragmentManager(), "SettingsApp");
            return true;
        });
    }

}
