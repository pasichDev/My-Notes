package com.pasich.mynotes.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.view.custom.TitleDialog;

public class CleanTrashView extends TitleDialog {

  public final ListView listView;
  private final Context context;
  private final TextView textMessage;

  public CleanTrashView(Context context, LayoutInflater inflater) {
    super(inflater);
    this.listView = new ListView(context);
    this.context = context;
    this.textMessage = new TextView(context);
    listView.setLayoutAnimation(
        new LayoutAnimationController(
            AnimationUtils.loadAnimation(listView.getContext(), R.anim.item_animation_dialog)));
    initialization();
  }

  private void initialization() {
    // setHeadTextView(context.getString(R.string.trashClean));
    setTextMessage();
    // getContainer().addView(listView);
    listView.setDivider(null);
  }

  private void setTextMessage() {
    textMessage.setText(context.getString(R.string.cleanTrashMessage));
    // setTextSizeMessage(textMessage);
    // getContainer().addView(textMessage, LP_DEFAULT);
  }
}
