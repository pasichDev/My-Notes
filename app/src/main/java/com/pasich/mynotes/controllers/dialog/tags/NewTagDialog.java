package com.pasich.mynotes.controllers.dialog.tags;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.utils.interfaces.ManageTag;
import com.pasich.mynotes.view.dialog.NewTagView;

public class NewTagDialog extends BottomSheetDialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final NewTagView NewTagView = new NewTagView(getLayoutInflater());
    final ManageTag ManageTag = (ManageTag) getContext();

    // NewTagView.setHeadTextView(getString(R.string.addTag));
    NewTagView.NewTagVIewUi.getSaveButton()
        .setOnClickListener(
            view -> {
              assert ManageTag != null;
              ManageTag.addTag(NewTagView.NewTagVIewUi.getInputTag().getText().toString(), 0, 0);
              dismiss();
            });

    //   builder.setContentView(NewTagView.getContainer());

    builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    ((InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE))
        .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);

    return builder;
  }

  @Override
  public void onDismiss(@NonNull DialogInterface dialog) {
    super.onDismiss(dialog);
    requireActivity()
        .getWindow()
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
  }
}
