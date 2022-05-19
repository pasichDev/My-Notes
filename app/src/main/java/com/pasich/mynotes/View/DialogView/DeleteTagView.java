package com.pasich.mynotes.View.DialogView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Anim.ListViewAnimation;
import com.pasich.mynotes.View.CustomView.CustomHeadUIDialog;

public class DeleteTagView extends CustomHeadUIDialog {

  private final Context context;
  public final ListView listView;
  private final TextView textMessage;
  private final LinearLayout.LayoutParams LP_TEXT_MESSAGE =
      new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

  public DeleteTagView(Context context, LayoutInflater inflater) {
    super(context, inflater);
    this.listView = new ListView(context);
    this.context = context;
    this.textMessage = new TextView(context);
    ListViewAnimation.setListviewAnimationLeftToShow(listView);
    this.LP_TEXT_MESSAGE.setMargins(70, 30, 70, 30);
    initialization();
  }

  private void initialization() {
    setHeadTextView(context.getString(R.string.deleteSelectTag));
    getSaveButton().setVisibility(View.GONE);
    getCloseButton().setVisibility(View.GONE);
    setTextMessage();
    getContainer().addView(listView);
  }

  private void setTextMessage() {
    textMessage.setText(context.getString(R.string.deleteSelectTagTextMassage));
    setTextSizeMessage(textMessage);
    getContainer().addView(textMessage, LP_TEXT_MESSAGE);
  }
}
