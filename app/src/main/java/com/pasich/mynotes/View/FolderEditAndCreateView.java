package com.pasich.mynotes.View;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;

import com.pasich.mynotes.R;

public class FolderEditAndCreateView {

  protected final Context context;
  public EditText input;

  public FolderEditAndCreateView(Context rootContext) {
    this.context = rootContext;
    this.input = new EditText(rootContext);
    setInput();
  }

  /** Method that implements the initial settings of the input field */
  private void setInput() {
    input.setInputType(
        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    input.setLines(1);
    input.setMaxLines(1);
    input.setHint(context.getString(R.string.inputNameFolder));
    input.setEnabled(true);
    input.setFocusableInTouchMode(true);
    input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(25)});
  }

  /** A method that sets up an input field after entering text there */
  public void setToTextInput() {
    input.setFocusable(true);
    input.requestFocus();
    input.setSelection(input.getText().length());
  }

  /**
   * Method that returns the string entered in the input field
   *
   * @return - Data in String format
   */
  public String getTextInput() {
    return input.getText().toString();
  }
}
