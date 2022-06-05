package com.pasich.mynotes.view.dialog;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.view.ListDialogView;

public class DeleteTagView extends ListDialogView {

  public DeleteTagView(LayoutInflater inflater) {
    super(inflater);
    addTitle("");
    initialization();
  }

  private void initialization() {
    setTextMessage();
    getRootContainer().addView(getItemsView());
  }

  private void setTextMessage() {
    TextView textMessage = new TextView(getContextRoot());
    textMessage.setText(getContextRoot().getString(R.string.deleteSelectTagTextMassage));
    textMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
    getRootContainer().addView(textMessage, getLayoutParamDefault());
  }
}
