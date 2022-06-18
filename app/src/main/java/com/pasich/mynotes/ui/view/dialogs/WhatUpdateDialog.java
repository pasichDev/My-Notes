package com.pasich.mynotes.ui.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.customView.ListDialogView;

public class WhatUpdateDialog extends BottomSheetDialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
    ListDialogView view = new ListDialogView(getLayoutInflater());
    view.addTitle(BuildConfig.VERSION_NAME);

    TextView textMessage = new TextView(getContext());
    textMessage.setText(getString(R.string.updateNowM));
    textMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, view.TEXT_MESSAGE_SIZE);
    view.addView(textMessage, view.LP_DEFAULT);
    builder.setContentView(view.getRootContainer());

    return builder;
  }
}
