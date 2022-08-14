package com.pasich.mynotes.ui.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.dialogs.settings.AboutAppDialog;

public class FragmentSettings extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_prefences, rootKey);
        final Preference aboutApp = findPreference("aboutApp");
        final Preference settingsApp = findPreference("settingsApp");
        final Preference settingsVoice = findPreference("settingsVoice");
        final Preference emailSend = findPreference("emailSend");

        assert aboutApp != null;
        aboutApp.setSummary("My Notes (v" + BuildConfig.VERSION_NAME + ")");
        aboutApp.setOnPreferenceClickListener(preference -> {
            new AboutAppDialog().show(getParentFragmentManager(), "AboutApp");
            return true;
        });
        assert settingsApp != null;
        settingsApp.setOnPreferenceClickListener(preference -> {
            changeFragment(new FragmentMain());
            return true;
        });
        assert settingsVoice != null;
        settingsVoice.setOnPreferenceClickListener(preference -> {
            changeFragment(new FragmentVoice());
            return true;
        });
        assert emailSend != null;
        emailSend.setOnPreferenceClickListener(preference -> {
            sendEmail();
            return true;
        });
    }

    private void changeFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }


    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:pasichDev@outlook.com"));
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
