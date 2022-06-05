package com.pasich.mynotes.view.customView;

import static com.pasich.mynotes.utils.Constants.TagSettingsConst.MAX_NAME_TAG;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pasich.mynotes.databinding.NewTagViewBinding;
import com.pasich.mynotes.utils.Simplifications.TextValidatorUtils;

public class NewTagView {

  private final NewTagViewBinding binding;

  public NewTagView(LayoutInflater inflater) {
    this.binding = NewTagViewBinding.inflate(inflater);
    this.initialization();
  }

  private void initialization() {
    binding.setErrorText(false);
    binding.setEnableButtonSave(false);
    validateNameActivate();
  }

  public final Button getSaveButton() {
    return binding.saveTag;
  }

  public final EditText getInputTag() {
    return binding.inputNameTag;
  }

  public final View getInputLayout() {
    return this.binding.getRoot();
  }

  private void validateNameActivate() {
    getInputTag()
        .addTextChangedListener(
            new TextValidatorUtils(getInputTag()) {
              @Override
              public void validate(TextView textView, String text) {
                validateText(text.length());
              }
            });
  }

  public void validateText(int length) {
    if (length >= MAX_NAME_TAG + 1) {
      binding.setErrorText(true);
      binding.setEnableButtonSave(false);
    } else if (length == MAX_NAME_TAG) {
      binding.setErrorText(false);
      binding.setEnableButtonSave(true);
    }
    if (length < 1) binding.setEnableButtonSave(false);
    else if (length < MAX_NAME_TAG - 1) binding.setEnableButtonSave(true);
  }
}
