package com.pasich.mynotes.ui.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.dialogs.settings.aboutAppDialog.AboutAppDialog;

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


    @Deprecated
    private void sendEmail() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            intent.putExtra(Intent.EXTRA_EMAIL, "pasichDev@outlook.com");
            this.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }
}
