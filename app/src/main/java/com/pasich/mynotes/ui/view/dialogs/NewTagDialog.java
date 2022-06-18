package com.pasich.mynotes.ui.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.TagView;
import com.pasich.mynotes.otherClasses.view.custom.InputTagView;

public class NewTagDialog extends BottomSheetDialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final InputTagView mView = new InputTagView(getLayoutInflater());
    final TagView tagView = (TagView) getContext();

    mView.addTitle(getString(R.string.addTag));
    mView.addView(mView.getNewTagView());
    mView
        .getSaveButton()
        .setOnClickListener(
            view -> {
              assert tagView != null;
              tagView.addTag(mView.getInputTag().getText().toString());

              dismiss();
            });

    builder.setContentView(mView.getRootContainer());

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
