package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Interface.AddTag;
import com.pasich.mynotes.Utils.Simplifications.TextValidatorUtils;
import com.pasich.mynotes.View.DialogView.NewTagView;

public class NewTagDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final NewTagView NewTagView = new NewTagView(requireContext(), getLayoutInflater());
    final AddTag AddTag = (AddTag) getContext();
    NewTagView.uiDialog.getCloseButton().setOnClickListener(view -> dismiss());
    NewTagView.uiDialog
        .getSaveButton()
        .setOnClickListener(
            view -> {
              assert AddTag != null;
              AddTag.addTagQuery(NewTagView.inputNameTag.getText().toString());
              dismiss();
            });

    builder.setContentView(NewTagView.uiDialog.getContainer());

    NewTagView.inputNameTag.addTextChangedListener(
        new TextValidatorUtils(NewTagView.inputNameTag) {
          @Override
          public void validate(TextView textView, String text) {
            if (text.length() == 11) {
              NewTagView.textMessageError.setVisibility(View.VISIBLE);
              NewTagView.uiDialog.getSaveButton().setEnabled(false);
            } else if (text.length() == 10) {
              NewTagView.textMessageError.setVisibility(View.GONE);
              NewTagView.uiDialog.getSaveButton().setEnabled(true);
            }
          }
        });

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