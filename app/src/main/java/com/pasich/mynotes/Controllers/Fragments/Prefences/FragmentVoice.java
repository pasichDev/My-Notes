package com.pasich.mynotes.Controllers.Fragments.Prefences;

import static android.speech.SpeechRecognizer.isRecognitionAvailable;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.Dialogs.TtsErrorDialog;
import com.pasich.mynotes.R;

public class FragmentVoice extends PreferenceFragmentCompat {
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.voice_prefences, rootKey);

    Preference errorSpechService = findPreference("errorSpechService");
    if (!isRecognitionAvailable(getContext())) {
      findPreference("spechLaunguage").setVisible(false);
      findPreference("setSpechOutputText").setVisible(false);
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
