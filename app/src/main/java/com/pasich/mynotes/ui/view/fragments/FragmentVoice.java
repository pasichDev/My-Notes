package com.pasich.mynotes.ui.view.fragments;

import static android.speech.SpeechRecognizer.isRecognitionAvailable;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.activity.SettingsActivity;

public class FragmentVoice extends PreferenceFragmentCompat implements SettingsActivity.IOnBackPressed {
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.voice_prefences, rootKey);
    final Preference errorSpechService = findPreference("errorSpechService");
    final Preference speechLanguage = findPreference("speechLanguage");
    final Preference setSpechOutputText = findPreference("setSpechOutputText");

    if (!isRecognitionAvailable(getContext())) {
      assert speechLanguage != null;
      speechLanguage.setVisible(false);
      assert setSpechOutputText != null;
      setSpechOutputText.setVisible(false);
      assert errorSpechService != null;
      errorSpechService.setVisible(true);
    }

    assert errorSpechService != null;
    errorSpechService.setOnPreferenceClickListener(
        preference -> {

          return true;
        });
  }


  @Override
  public boolean onBackPressed() {
    return true;
  }

}
