package com.pasich.mynotes.View.CustomView;

import static com.pasich.mynotes.Utils.Constants.TagSettingsConst.MAX_NAME_TAG;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Simplifications.TextValidatorUtils;

public class NewTagVIewUi {

  private final Button saveButton;
  private final TextView errorMessage;
  private final EditText inputTag;
  protected View inputLayoutUI;
  protected int inputLayout = R.layout.new_tag_layout;

  public NewTagVIewUi(LayoutInflater inflater) {
    this.inputLayoutUI = inflater.inflate(this.inputLayout, null);
    this.saveButton = inputLayoutUI.findViewById(R.id.saveTag);
    this.errorMessage = inputLayoutUI.findViewById(R.id.errorText);
    this.inputTag = inputLayoutUI.findViewById(R.id.inputNameTag);
    validateNameActivate();
  }


  public final Button getSaveButton() {
    return this.saveButton;
  }

  public final EditText getInputTag() {
    return this.inputTag;
  }

  public final TextView getErrorMessage() {
    return this.errorMessage;
  }

  public final View getInputLayoutUI() {
    return this.inputLayoutUI;
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

  public void setInputNormal() {
    inputTag.setBackgroundResource(R.drawable.background_normal_input);
    inputTag.setPadding(20, 20, 20, 20);
  }

  public void setInputError() {
    inputTag.setBackgroundResource(R.drawable.background_normal_error);
    inputTag.setPadding(20, 20, 20, 20);
  }

  public void validateText(int length) {
    if (length == MAX_NAME_TAG + 1) {
      getErrorMessage().setVisibility(View.VISIBLE);
      getSaveButton().setEnabled(false);
      setInputError();
    } else if (length == MAX_NAME_TAG) {
      getErrorMessage().setVisibility(View.INVISIBLE);
      getSaveButton().setEnabled(true);
      setInputNormal();
    }
    if (length < 1) getSaveButton().setEnabled(false);
    else if (length < MAX_NAME_TAG - 1) getSaveButton().setEnabled(true);
  }
}
