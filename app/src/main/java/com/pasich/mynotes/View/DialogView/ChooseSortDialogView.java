package com.pasich.mynotes.View.DialogView;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.pasich.mynotes.Utils.Anim.ListViewAnimation;
import com.pasich.mynotes.View.CustomView.CustomHeadUIDialog;

public class ChooseSortDialogView extends CustomHeadUIDialog {

  public final ListView listView;

  public ChooseSortDialogView(Context context, LayoutInflater inflater) {
    super(context, inflater);
    this.listView = new ListView(context);
    initialization();
  }

  private void initialization() {
    ListViewAnimation.setListviewAnimationLeftToShow(listView);
    listView.setDivider(null);
    getContainer().addView(listView);
  }
}
