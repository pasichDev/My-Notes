package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomHeadUIDialog;


public class WhatUpdateDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
    CustomHeadUIDialog uiDialog = new CustomHeadUIDialog(getContext(), getLayoutInflater());
    uiDialog.setHeadTextView(BuildConfig.VERSION_NAME);
    TextView textMessage = new TextView(getContext());

    textMessage.setText(getString(R.string.updateNowM));
    uiDialog.setTextSizeMessage(textMessage);
    uiDialog.getContainer().addView(textMessage, uiDialog.LP_DEFAULT);

    uiDialog.getSaveButton().setVisibility(View.GONE);
    uiDialog.getCloseButton().setVisibility(View.GONE);
    builder.setContentView(uiDialog.getContainer());

    return builder;
  }
}
