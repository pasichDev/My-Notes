package com.pasich.mynotes.controllers.Fragments;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.controllers.Dialogs.EditThemeColorDialog;

public class FragmentMain extends PreferenceFragmentCompat {
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.main_prefences, rootKey);
    final Preference themeColorEdit = findPreference("themeColor");

    assert themeColorEdit != null;
    themeColorEdit.setOnPreferenceClickListener(
        preference -> {
          new EditThemeColorDialog().show(getParentFragmentManager(), "EditColorTheme");
          return true;
        });
  }
}
