package com.pasich.mynotes.ui.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.dialogs.settings.AboutAppDialog;
import com.pasich.mynotes.ui.view.dialogs.settings.FeedbackDialog;

public class FragmentSettings extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_prefences, rootKey);
        final Preference aboutApp = findPreference("aboutApp");
        final Preference settingsVoice = findPreference("settingsVoice");
        final Preference feedback = findPreference("feedback");

        assert aboutApp != null;
        aboutApp.setSummary("My Notes (v" + BuildConfig.VERSION_NAME + ")");
        aboutApp.setOnPreferenceClickListener(preference -> {
            new AboutAppDialog().show(getParentFragmentManager(), "AboutApp");
            return true;
        });

        assert settingsVoice != null;
        settingsVoice.setOnPreferenceClickListener(preference -> {
            changeFragment(new FragmentVoice());
            return true;
        });
        assert feedback != null;
        feedback.setOnPreferenceClickListener(preference -> {
            new FeedbackDialog().show(getParentFragmentManager(), "feedbackDialog");
            return true;
        });
    }

    private void changeFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }



}
