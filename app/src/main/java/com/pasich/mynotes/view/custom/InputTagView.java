package com.pasich.mynotes.view.custom;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_NAME_TAG;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pasich.mynotes.databinding.NewTagViewBinding;
import com.pasich.mynotes.utils.simplifications.TextValidatorUtils;
import com.pasich.mynotes.view.base.BaseView;

public class InputTagView extends BaseView {

  private final NewTagViewBinding binding;

  public InputTagView(LayoutInflater inflater) {
    super(inflater);
    this.binding = NewTagViewBinding.inflate(inflater);
    this.initialization();
  }

  private void initialization() {
    binding.setErrorText(false);
    binding.setEnableButtonSave(false);
    validateNameActivate();
    getInputTag().requestFocus();
  }

  public final Button getSaveButton() {
    return binding.saveTag;
  }

  public final EditText getInputTag() {
    return binding.inputNameTag;
  }

  public final View getNewTagView() {
    return binding.getRoot();
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
