package com.pasich.mynotes.View.DialogView;

import android.content.Context;
import android.view.LayoutInflater;

import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomHeadUIDialog;
import com.pasich.mynotes.View.CustomView.NewTagVIewUi;

public class NewTagView extends CustomHeadUIDialog {

  private final Context context;
  public final NewTagVIewUi NewTagVIewUi;

  public NewTagView(Context context, LayoutInflater inflater) {
    super(context, inflater);
    this.context = context;
    this.NewTagVIewUi = new NewTagVIewUi(inflater);
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
