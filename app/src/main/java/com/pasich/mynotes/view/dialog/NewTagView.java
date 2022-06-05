package com.pasich.mynotes.view.dialog;

import android.view.LayoutInflater;

import com.pasich.mynotes.view.BaseView;

public class NewTagView extends BaseView {

  public final com.pasich.mynotes.view.custom.NewTagView NewTagVIewUi;

  public NewTagView(LayoutInflater inflater) {
    super(inflater);
    this.NewTagVIewUi = new com.pasich.mynotes.view.custom.NewTagView(inflater);
    setInputNameTag();
  }

  private void setInputNameTag() {
    // getContainer().addView(NewTagVIewUi.getInputLayout());
    NewTagVIewUi.getInputTag().requestFocus();
  }
}
