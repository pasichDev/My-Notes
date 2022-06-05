package com.pasich.mynotes.view.DialogView;

import android.content.Context;
import android.view.LayoutInflater;

import com.pasich.mynotes.R;
import com.pasich.mynotes.view.CustomView.TitleDialog;

public class NewTagView extends TitleDialog {

  private final Context context;
  public final com.pasich.mynotes.view.CustomView.NewTagView NewTagVIewUi;

  public NewTagView(Context context, LayoutInflater inflater) {
    super(context, inflater);
    this.context = context;
    this.NewTagVIewUi = new com.pasich.mynotes.view.CustomView.NewTagView(inflater);
    initialization();
  }

  private void initialization() {
    setHeadTextView(context.getString(R.string.addTag));
    setInputNameTag();
    setErrorMessage();
    NewTagVIewUi.getSaveButton().setEnabled(false);
  }



  private void setInputNameTag() {
    getContainer().addView(NewTagVIewUi.getInputLayoutUI());
    NewTagVIewUi.getInputTag().setHint(context.getString(R.string.nameTagInput));
    NewTagVIewUi.getInputTag().requestFocus();
  }

  private void setErrorMessage() {
    NewTagVIewUi.getErrorMessage().setText(context.getString(R.string.errorCountNameTag));
  }

}
