package com.pasich.mynotes.view.dialog;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.view.base.ListDialogView;

public class CleanTrashView extends ListDialogView {

  public CleanTrashView(LayoutInflater inflater) {
    super(inflater);
    addTitle(getContextRoot().getString(R.string.trashClean));
    initialization();
  }

  private void initialization() {
    setTextMessage();
    addView(getItemsView());
  }

  private void setTextMessage() {
    TextView textMessage = new TextView(getContextRoot());
    textMessage.setText(getContextRoot().getString(R.string.cleanTrashMessage));
    textMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_MESSAGE_SIZE);
    addView(textMessage, LP_DEFAULT);
  }
}
