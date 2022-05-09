package com.pasich.mynotes.View.DialogView;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Spinner;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomUIDialog;

public class CopyNotesView {

  public final TextView textMessage;
  public final Spinner spinner;
  public CustomUIDialog uiDialog;

  public CopyNotesView(Context context, LayoutInflater inflater) {
    this.textMessage = new TextView(context);
    this.uiDialog = new CustomUIDialog(context, inflater);
    this.spinner = new Spinner(context);

    setUiDialog();
    setTextMessage();
  }

  private void setUiDialog() {
    uiDialog.setHeadTextView(String.valueOf(R.string.copyNotesTo));
    uiDialog.getContainer().addView(textMessage, uiDialog.lp);
    uiDialog.getContainer().addView(spinner, uiDialog.lp);
  }

  private void setTextMessage() {
    textMessage.setText(R.string.copyNotefor);
    uiDialog.setTextSizeMessage(textMessage);
  }
}
