package com.pasich.mynotes.Dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;

public class errorTTS extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            return builder
                    .setTitle(getString(R.string.error_TTS))
                    .setMessage(getString(R.string.error_TTS_Message))
                    .setPositiveButton(getString(R.string.open_PlayMarket), (dialog, which) -> {
                        final String appPackageName = "com.google.android.tts";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), null)
                    .create();
    }

}
