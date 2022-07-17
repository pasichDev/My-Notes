package com.pasich.mynotes.ui.view.fragments;


import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.activity.SettingsActivity;

public class FragmentMain extends PreferenceFragmentCompat implements SettingsActivity.IOnBackPressed {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.main_prefences, rootKey);
    }


    @Override
    public boolean onBackPressed() {
        return true;
    }


}
