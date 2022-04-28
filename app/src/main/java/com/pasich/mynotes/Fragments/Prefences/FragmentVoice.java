package com.pasich.mynotes.Fragments.Prefences;

import static android.speech.SpeechRecognizer.isRecognitionAvailable;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.Dialogs.errorTTS;
import com.pasich.mynotes.R;

public class FragmentVoice extends PreferenceFragmentCompat {
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.voice_prefences, rootKey);

    Preference errorSpechService = findPreference("errorSpechService");
    // Проверим на доступность настроект распознавание речи
    if (!isRecognitionAvailable(getContext())) {
      findPreference("spechLaunguage").setVisible(false);
      findPreference("setSpechOutputText").setVisible(false);
      errorSpechService.setVisible(true);
    }

    assert errorSpechService != null;
    errorSpechService.setOnPreferenceClickListener(
        preference -> {
          new errorTTS().show(getParentFragmentManager(), "Error Service Spech");
          return true;
        });
  }
}
