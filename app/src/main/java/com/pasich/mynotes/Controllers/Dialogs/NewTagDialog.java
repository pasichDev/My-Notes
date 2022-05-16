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
  public NewTagView NewTagView;

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final AddTag AddTag = (AddTag) getContext();
    NewTagView = new NewTagView(requireContext(), getLayoutInflater());
    NewTagView.getCloseButton().setOnClickListener(view -> dismiss());
    NewTagView.getSaveButton()
        .setOnClickListener(
            view -> {
              assert AddTag != null;
              AddTag.addTagQuery(NewTagView.inputNameTag.getText().toString());
              dismiss();
            });

    builder.setContentView(NewTagView.getContainer());

    NewTagView.inputNameTag.addTextChangedListener(
        new TextValidatorUtils(NewTagView.inputNameTag) {
          @Override
          public void validate(TextView textView, String text) {
            validateText(text);
          }
        });

    builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    ((InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE))
        .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);

    return builder;
  }

  private void validateText(String text) {
    if (text.length() == 21) {
      NewTagView.textMessageError.setVisibility(View.VISIBLE);
      NewTagView.getSaveButton().setEnabled(false);
      NewTagView.setInputError();
    } else if (text.length() == 20) {
      NewTagView.textMessageError.setVisibility(View.GONE);
      NewTagView.getSaveButton().setEnabled(true);
      NewTagView.setInputNormal();
    }
  }

  @Override
  public void onDismiss(@NonNull DialogInterface dialog) {
    super.onDismiss(dialog);
    requireActivity()
        .getWindow()
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
  }
}
