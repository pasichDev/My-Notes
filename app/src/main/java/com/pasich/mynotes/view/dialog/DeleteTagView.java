package com.pasich.mynotes.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.view.custom.TitleDialog;

public class DeleteTagView extends TitleDialog {

  private final Context context;
  public final ListView listView;
  private final TextView textMessage;

  public DeleteTagView(Context context, LayoutInflater inflater) {
    super(context, inflater);
    this.listView = new ListView(context);
    this.context = context;
    this.textMessage = new TextView(context);
    listView.setLayoutAnimation(
        new LayoutAnimationController(
            AnimationUtils.loadAnimation(listView.getContext(), R.anim.item_animation_dialog)));

    initialization();
  }

  private void initialization() {
    setHeadTextView(context.getString(R.string.deleteSelectTag));
    setTextMessage();
    getContainer().addView(listView);
  }

  private void setTextMessage() {
    textMessage.setText(context.getString(R.string.deleteSelectTagTextMassage));
    setTextSizeMessage(textMessage);
    getContainer().addView(textMessage, LP_DEFAULT);
  }
}
