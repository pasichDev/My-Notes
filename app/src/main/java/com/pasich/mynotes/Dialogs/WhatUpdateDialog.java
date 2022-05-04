package com.pasich.mynotes.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.lib.CustomUIDialog;

import java.util.Objects;

public class WhatUpdateDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    String versionName = BuildConfig.VERSION_NAME;

    CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());
    uiDialog.setHeadTextView(getString(R.string.whatUpdate));
    TextView textMessage = new TextView(getContext());

    textMessage.setText(getString(R.string.updateNowM));
    uiDialog.setTextSizeMessage(textMessage);
    uiDialog.getContainer().addView(textMessage, uiDialog.lp);

    uiDialog.getCloseButton().setVisibility(View.VISIBLE);
    uiDialog
        .getCloseButton()
        .setOnClickListener(view -> Objects.requireNonNull(getDialog()).dismiss());
    builder.setView(uiDialog.getContainer());

    return builder.create();
  }
}
