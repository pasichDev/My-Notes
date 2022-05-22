package com.pasich.mynotes.View.DialogView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Anim.ListViewAnimation;
import com.pasich.mynotes.View.CustomView.CustomHeadUIDialog;

public class ChooseTagDialogView extends CustomHeadUIDialog {

  public final ListView listView;
  private final View inputAdd;

  @SuppressLint("InflateParams")
  public ChooseTagDialogView(Context context, LayoutInflater inflater) {
    super(context, inflater);
    this.listView = new ListView(context);
    this.inputAdd = inflater.inflate(R.layout.new_tag_layout, null);
    ListViewAnimation.setListviewAnimationLeftToShow(listView);
    initialization();
  }

  private void initialization() {
    listView.setDivider(null);
    getContainer().addView(inputAdd);
    getContainer().addView(listView);
  }
}
