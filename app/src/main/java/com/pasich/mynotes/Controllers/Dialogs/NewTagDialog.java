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
import com.pasich.mynotes.Utils.Interface.ManageTag;
import com.pasich.mynotes.Utils.Simplifications.TextValidatorUtils;
import com.pasich.mynotes.View.DialogView.NewTagView;

public class NewTagDialog extends DialogFragment {
  public NewTagView NewTagView;

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ManageTag ManageTag = (ManageTag) getContext();
    NewTagView = new NewTagView(requireContext(), getLayoutInflater());
    NewTagView.getCloseButton().setOnClickListener(view -> dismiss());
    NewTagView.getSaveButton()
        .setOnClickListener(
            view -> {
              assert ManageTag != null;
              ManageTag.addTag(NewTagView.inputNameTag.getText().toString());
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

  /**
   * Метод который реализовует валидацию названия метки которую вводит пользователь Одно правило, не
   * больше 20 символов
   *
   * @param text - названия метки
   */
  private void validateText(String text) {
    if (text.length() == 21) {
      NewTagView.textMessageError.setVisibility(View.VISIBLE);
      NewTagView.getSaveButton().setEnabled(false);
      NewTagView.setInputError();
    } else if (text.length() == 20) {
      NewTagView.textMessageError.setVisibility(View.GONE);
      NewTagView.getSaveButton().setEnabled(true);
      NewTagView.setInputNormal();
    } else if (text.length() < 3) NewTagView.getSaveButton().setEnabled(false);
    else {
      text.length();
      NewTagView.getSaveButton().setEnabled(true);
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
