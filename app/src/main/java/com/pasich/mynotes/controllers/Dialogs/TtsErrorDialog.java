package com.pasich.mynotes.controllers.Dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomHeadUIDialog;

public class TtsErrorDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    CustomHeadUIDialog uiDialog = new CustomHeadUIDialog(getContext(), getLayoutInflater());
    uiDialog.setHeadTextView(getString(R.string.error));
    TextView textMessage1 = new TextView(getContext());
    TextView textMessage2 = new TextView(getContext());
    textMessage1.setText(getString(R.string.error_TTS));

    textMessage2.setText(getString(R.string.error_TTS_Message));
    textMessage1.setTypeface(null, Typeface.BOLD);
    uiDialog.setTextSizeMessage(textMessage1);
    uiDialog.setTextSizeMessage(textMessage2);
    uiDialog.getContainer().addView(textMessage1, uiDialog.LP_DEFAULT);
    uiDialog.getContainer().addView(textMessage2, uiDialog.LP_DEFAULT);

    builder.setView(uiDialog.getContainer());

    return builder
        .setPositiveButton(
            getString(R.string.open_PlayMarket),
            (dialog, which) -> {
              final String appPackageName = "com.google.android.tts";
              try {
                startActivity(
                    new Intent(
                        Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
              } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(
                    new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            "http://play.google.com/store/apps/details?id=" + appPackageName)));
              }
            })
        .create();
  }
}
