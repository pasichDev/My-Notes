package com.pasich.mynotes.view.DialogView;

import android.content.Context;
import android.view.LayoutInflater;

import com.pasich.mynotes.view.customView.TitleDialog;

public class NewTagView extends TitleDialog {

  public final com.pasich.mynotes.view.customView.NewTagView NewTagVIewUi;

  public NewTagView(Context context, LayoutInflater inflater) {
    super(context, inflater);
    this.NewTagVIewUi = new com.pasich.mynotes.view.customView.NewTagView(inflater);
    setInputNameTag();
  }

  private void setInputNameTag() {
    getContainer().addView(NewTagVIewUi.getInputLayout());
    NewTagVIewUi.getInputTag().requestFocus();
  }
}
