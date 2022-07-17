package com.pasich.mynotes.ui.view.fragments;


import android.os.Bundle;
import android.widget.TextView;

import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.activity.SettingsActivity;

public class FragmentMain extends PreferenceFragmentCompat implements SettingsActivity.IOnBackPressed {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.main_prefences, rootKey);
        TextView textTitleActivity = requireActivity().findViewById(R.id.titleActivity);
        textTitleActivity.setText(R.string.settingsApp);
    }


    @Override
    public boolean onBackPressed() {
        return true;
    }


}
