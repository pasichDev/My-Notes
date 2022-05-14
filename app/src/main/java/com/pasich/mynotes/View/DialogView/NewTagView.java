package com.pasich.mynotes.View.DialogView;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomUIDialog;

public class NewTagView {

  public final TextView textMessageError;
  private final Context context;
  public CustomUIDialog uiDialog;
  public final EditText inputNameTag;

  public NewTagView(Context context, LayoutInflater inflater) {
    this.context = context;
    this.textMessageError = new TextView(context);
    this.uiDialog = new CustomUIDialog(context, inflater);
    this.inputNameTag = new EditText(context);

    initialization();
  }

  private void initialization() {
    uiDialog.setHeadTextView(context.getString(R.string.addTag));
    setInputNameTag();
    setTextMessageError();
  }

  private void setTextMessageError() {

    textMessageError.setTextColor(context.getColor(R.color.red));
    textMessageError.setVisibility(View.GONE);
    textMessageError.setText(context.getString(R.string.errorCountNameTag));
    uiDialog.getContainer().addView(textMessageError, uiDialog.lp);
  }

  private void setInputNameTag() {
    inputNameTag.setBackgroundResource(R.drawable.edittext_form);
    inputNameTag.setPadding(20, 20, 20, 20);
    inputNameTag.setHint(context.getString(R.string.nameTagInput));
    inputNameTag.setInputType(
        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    inputNameTag.setMaxLines(1);
    inputNameTag.setEnabled(true);
    inputNameTag.setFocusable(true);
    inputNameTag.setFocusableInTouchMode(true);
    inputNameTag.requestFocus();
    uiDialog.getContainer().addView(inputNameTag, uiDialog.lp);
  }
}
