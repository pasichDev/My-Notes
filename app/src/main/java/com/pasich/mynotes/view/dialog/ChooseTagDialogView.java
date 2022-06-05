package com.pasich.mynotes.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.R;
import com.pasich.mynotes.view.base.TitleDialogView;
import com.pasich.mynotes.view.custom.NewTagView;

public class ChooseTagDialogView extends TitleDialogView {

  public final ListView listView;
  public final NewTagView NewTagVIewUi;
  public final TabLayout TabLayoutTags;

  @SuppressLint("InflateParams")
  public ChooseTagDialogView(Context context, LayoutInflater inflater) {
    super(inflater);
    this.listView = new ListView(context);
    this.NewTagVIewUi = new NewTagView(inflater);
    this.TabLayoutTags = new TabLayout(context);
    listView.setLayoutAnimation(
        new LayoutAnimationController(
            AnimationUtils.loadAnimation(listView.getContext(), R.anim.item_animation_dialog)));

    initialization();
    setTabLayoutTags();
  }

  private void setTabLayoutTags() {}

  private void initialization() {
    NewTagVIewUi.getSaveButton().setEnabled(false);
    // getContainer().addView(TabLayoutTags, LP_DEFAULT);
    // getContainer().addView(NewTagVIewUi.getInputLayout());
  }
}
