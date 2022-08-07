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
                    new ErrorTtsDialog().show(getParentFragmentManager(),"errortts");
                    return true;
                });
    }


    @Override
    public boolean onBackPressed() {
        return true;
    }

}
