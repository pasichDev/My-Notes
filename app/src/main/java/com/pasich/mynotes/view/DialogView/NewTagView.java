package com.pasich.mynotes.view.DialogView;

import android.content.Context;
import android.view.LayoutInflater;

import com.pasich.mynotes.R;
import com.pasich.mynotes.view.customView.TitleDialog;

public class NewTagView extends TitleDialog {

  private final Context context;
  public final com.pasich.mynotes.view.customView.NewTagView NewTagVIewUi;

  public NewTagView(Context context, LayoutInflater inflater) {
    super(context, inflater);
    this.context = context;
    this.NewTagVIewUi = new com.pasich.mynotes.view.customView.NewTagView(inflater);
    initialization();
  }

  private void initialization() {
    setHeadTextView(context.getString(R.string.addTag));
    setInputNameTag();
    NewTagVIewUi.getSaveButton().setEnabled(false);
  }



  private void setInputNameTag() {
    getContainer().addView(NewTagVIewUi.getInputLayout());
    NewTagVIewUi.getInputTag().setHint(context.getString(R.string.nameTagInput));
    NewTagVIewUi.getInputTag().requestFocus();
  }



}
