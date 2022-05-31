package com.pasich.mynotes.controllers.Fragments;

import static android.speech.SpeechRecognizer.isRecognitionAvailable;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.controllers.Dialogs.TtsErrorDialog;

public class FragmentVoice extends PreferenceFragmentCompat {
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
          new TtsErrorDialog().show(getParentFragmentManager(), "Error Service Spech");
          return true;
        });
  }
}
