package com.pasich.mynotes.Controllers.Fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.R;

public class FragmentBackup extends PreferenceFragmentCompat {

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.backup_prefences, rootKey);

  }

}
