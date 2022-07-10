package com.pasich.mynotes.ui.view.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.R;

public class FragmentBackup extends PreferenceFragmentCompat {

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.backup_prefences, rootKey);

  }

}
