package com.pasich.mynotes.ui.view.fragments;

import android.os.Bundle;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.dialogs.settings.aboutAppDialog.AboutAppDialog;

public class FragmentMain extends PreferenceFragmentCompat {
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.main_prefences, rootKey);
    final Preference aboutApp = findPreference("aboutApp");

    assert aboutApp != null;
    aboutApp.setOnPreferenceClickListener(preference -> {
      Log.wtf("pasic", "onCreatePreferences: " );
      new AboutAppDialog().show(getParentFragmentManager(), "AboutApp");
      return true;
    });
  }
}
