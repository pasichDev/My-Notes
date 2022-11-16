package com.pasich.mynotes.ui.view.dialogs.settings;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;

public class WhatUpdateDialog extends BottomSheetDialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
      final BottomSheetDialog builder = new BottomSheetDialog(requireActivity(), R.style.BottomSheetsStyleCustom);
      builder.setContentView(R.layout.dialog_new_update);
      builder.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
      MaterialTextView title = builder.findViewById(R.id.headTextDialog);
      assert title != null;
      title.setText(BuildConfig.VERSION_NAME);

      MaterialTextView message = builder.findViewById(R.id.textMessageDialog);
      assert message != null;
      message.setText(getString(R.string.updateNowM));

      return builder;
  }
}
