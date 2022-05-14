package com.pasich.mynotes.Utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public abstract class TextValidator implements TextWatcher {
  private final TextView textView;

  public TextValidator(TextView textView) {
    this.textView = textView;
  }

  public abstract void validate(TextView textView, String text);

  @Override
  public final void afterTextChanged(Editable s) {
    String text = textView.getText().toString();
    validate(textView, text);
  }

  @Override
  public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
    /* Don't care */
  }

  @Override
  public final void onTextChanged(CharSequence s, int start, int before, int count) {
    /* Don't care */
  }
}
