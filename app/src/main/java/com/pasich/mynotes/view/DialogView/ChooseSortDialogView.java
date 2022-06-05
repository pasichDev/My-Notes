package com.pasich.mynotes.view.DialogView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.view.customView.TitleDialog;

public class ChooseSortDialogView extends TitleDialog {

  public final ListView listView;

  public ChooseSortDialogView(Context context, LayoutInflater inflater) {
    super(context, inflater);
    this.listView = new ListView(context);
    initialization();
  }

  private void initialization() {
    listView.setLayoutAnimation(
        new LayoutAnimationController(
            AnimationUtils.loadAnimation(listView.getContext(), R.anim.item_animation_dialog)));
    listView.setDivider(null);
    getContainer().addView(listView);
  }
}
