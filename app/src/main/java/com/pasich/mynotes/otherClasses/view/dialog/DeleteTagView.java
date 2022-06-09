package com.pasich.mynotes.otherClasses.view.dialog;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.otherClasses.view.base.ListDialogView;

public class DeleteTagView extends ListDialogView {

  public DeleteTagView(LayoutInflater inflater) {
    super(inflater);
    addTitle(getContextRoot().getString(R.string.deleteTag));
    initialization();
  }

  private void initialization() {
    setTextMessage();
    addView(getItemsView());
  }

  private void setTextMessage() {
    TextView textMessage = new TextView(getContextRoot());
    textMessage.setText(getContextRoot().getString(R.string.deleteSelectTagTextMassage));
    textMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_MESSAGE_SIZE);
    addView(textMessage, LP_DEFAULT);
  }
}
