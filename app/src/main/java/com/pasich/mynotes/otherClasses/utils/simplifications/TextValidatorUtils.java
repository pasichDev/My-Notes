package com.pasich.mynotes.otherClasses.utils.simplifications;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/** A class that simplifies the use of TextWatcher */
public abstract class TextValidatorUtils implements TextWatcher {
  private final TextView textView;

  public TextValidatorUtils(TextView textView) {
    this.textView = textView;
  }

  public abstract void validate(TextView textView, String text);

  @Override
  public final void afterTextChanged(Editable s) {
    String text = textView.getText().toString();
    validate(textView, text);
  }

  @Override
  public final void beforeTextChanged(CharSequence s, int start, int count, int after) {}

  @Override
  public final void onTextChanged(CharSequence s, int start, int before, int count) {}
}
