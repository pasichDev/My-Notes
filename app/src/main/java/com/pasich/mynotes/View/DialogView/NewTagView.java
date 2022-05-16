package com.pasich.mynotes.View.DialogView;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomHeadUIDialog;

public class NewTagView extends CustomHeadUIDialog {

  public final TextView textMessageError;
  public final EditText inputNameTag;
  private final Context context;
  private final LinearLayout.LayoutParams LP_INPUT =
      new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
  private final LinearLayout.LayoutParams LP_ERROR_MESSAGE =
      new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

  public NewTagView(Context context, LayoutInflater inflater) {
    super(context, inflater);
    this.context = context;
    this.textMessageError = new TextView(context);
    this.inputNameTag = new EditText(context);
    this.LP_INPUT.setMargins(70, 50, 70, 30);
    this.LP_ERROR_MESSAGE.setMargins(70, 0, 70, 50);
    initialization();
  }

  private void initialization() {
    setHeadTextView(context.getString(R.string.addTag));
    setInputNameTag();
    setTextMessageError();
    getSaveButton().setEnabled(false);
  }

  private void setTextMessageError() {
    textMessageError.setTextColor(context.getColor(R.color.red));
    textMessageError.setTextSize(14);
    textMessageError.setVisibility(View.GONE);
    textMessageError.setText(context.getString(R.string.errorCountNameTag));
    getContainer().addView(textMessageError, LP_ERROR_MESSAGE);
  }

  private void setInputNameTag() {
    setInputNormal();
    inputNameTag.setHint(context.getString(R.string.nameTagInput));
    inputNameTag.setInputType(
        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    inputNameTag.setMaxLines(1);
    inputNameTag.setEnabled(true);
    inputNameTag.setFocusable(true);
    inputNameTag.setFocusableInTouchMode(true);
    inputNameTag.requestFocus();
    getContainer().addView(inputNameTag, LP_INPUT);
  }

  public void setInputNormal() {
    inputNameTag.setBackgroundResource(R.drawable.background_normal_input);
    inputNameTag.setPadding(20, 20, 20, 20);
  }

  public void setInputError() {
    inputNameTag.setBackgroundResource(R.drawable.background_normal_error);
    inputNameTag.setPadding(20, 20, 20, 20);
  }
}
