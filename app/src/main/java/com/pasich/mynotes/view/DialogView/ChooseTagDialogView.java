package com.pasich.mynotes.view.DialogView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.R;
import com.pasich.mynotes.view.CustomView.NewTagView;
import com.pasich.mynotes.view.CustomView.TitleDialog;

public class ChooseTagDialogView extends TitleDialog {

  public final ListView listView;
  public final NewTagView NewTagVIewUi;
  public final TabLayout TabLayoutTags;

  @SuppressLint("InflateParams")
  public ChooseTagDialogView(Context context, LayoutInflater inflater) {
    super(context, inflater);
    this.listView = new ListView(context);
    this.NewTagVIewUi = new NewTagView(inflater);
    this.TabLayoutTags = new TabLayout(context);
    listView.setLayoutAnimation(
        new LayoutAnimationController(
            AnimationUtils.loadAnimation(listView.getContext(), R.anim.item_animation_dialog)));

    initialization();
    setTabLayoutTags();
    setErrorMessage(context);
  }

  private void setTabLayoutTags() {}

  private void initialization() {
    NewTagVIewUi.getSaveButton().setEnabled(false);
    getContainer().addView(TabLayoutTags, LP_DEFAULT);
    getContainer().addView(NewTagVIewUi.getInputLayoutUI());
  }

  private void setErrorMessage(Context context) {
    NewTagVIewUi.getErrorMessage().setText(context.getString(R.string.errorCountNameTag));
  }
}
