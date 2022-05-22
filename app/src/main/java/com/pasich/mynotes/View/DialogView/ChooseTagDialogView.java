package com.pasich.mynotes.View.DialogView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.pasich.mynotes.Utils.Anim.ListViewAnimation;
import com.pasich.mynotes.View.CustomView.CustomHeadUIDialog;
import com.pasich.mynotes.View.CustomView.NewTagVIewUi;

public class ChooseTagDialogView extends CustomHeadUIDialog {

  public final ListView listView;
  public final NewTagVIewUi NewTagVIewUi;

  @SuppressLint("InflateParams")
  public ChooseTagDialogView(Context context, LayoutInflater inflater) {
    super(context, inflater);
    this.listView = new ListView(context);
    this.NewTagVIewUi = new NewTagVIewUi(inflater);
    ListViewAnimation.setListviewAnimationLeftToShow(listView);
    initialization();
  }

  private void initialization() {
    listView.setDivider(null);
    getContainer().addView(NewTagVIewUi.getInputLayoutUI());
    getContainer().addView(listView);
  }
}
