package com.pasich.mynotes.Controllers.Fragments.Prefences;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.Dialogs.EditThemeColorDialog;
import com.pasich.mynotes.R;

public class FragmentMain extends PreferenceFragmentCompat implements EditThemeColorDialog.updateTheme {
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.main_prefences, rootKey);
    Preference themeColorEdit = findPreference("themeColor");

    assert themeColorEdit != null;
    themeColorEdit.setOnPreferenceClickListener(
        preference -> {
          EditThemeColorDialog editThemeColor = new EditThemeColorDialog(getContext());
          editThemeColor.setTargetFragment(this, 300);
          editThemeColor.show(getParentFragmentManager(), "ssss");
          return true;
        });
  }

  /** Метод который обновляет активность после смены цвета */
  @Override
  public void updateThemeCheck() {
    getActivity().finish();
    startActivity(getActivity().getIntent());
    getActivity().overridePendingTransition(0, 0);
  }
}
