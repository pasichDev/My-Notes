package com.pasich.mynotes.ui.view.fragments;

import static android.speech.SpeechRecognizer.isRecognitionAvailable;

import android.os.Bundle;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.activity.SettingsActivity;
import com.pasich.mynotes.ui.view.dialogs.settings.errorTts.ErrorTtsDialog;

public class FragmentVoice extends PreferenceFragmentCompat implements SettingsActivity.IOnBackPressed {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.voice_prefences, rootKey);
        TextView textTitleActivity = requireActivity().findViewById(R.id.titleActivity);
        textTitleActivity.setText(R.string.settingsVoice);

        final Preference errorSpeechService = findPreference("errorSpeechService");
        final Preference speechLanguage = findPreference("speechLanguage");
        final Preference setSpeechOutputText = findPreference("setSpeechOutputText");

        if (!isRecognitionAvailable(getContext())) {
            assert speechLanguage != null;
            speechLanguage.setVisible(false);
            assert setSpeechOutputText != null;
            setSpeechOutputText.setVisible(false);
            assert errorSpeechService != null;
            errorSpeechService.setVisible(true);
        }

        assert errorSpeechService != null;
        errorSpeechService.setOnPreferenceClickListener(
                preference -> {
                    new ErrorTtsDialog().show(getParentFragmentManager(), "errortts");
                    return true;
                });
    }


    @Override
    public boolean onBackPressed() {
        return true;
    }

}
