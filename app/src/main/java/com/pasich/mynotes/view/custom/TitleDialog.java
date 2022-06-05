package com.pasich.mynotes.view.custom;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasich.mynotes.databinding.HeadViewBinding;

/** A class that simplifies working with dialogs TitleDialog uiDialog = new */
public class TitleDialog {

  private final HeadViewBinding binding;
  public LinearLayout.LayoutParams LP_DEFAULT;
  protected LinearLayout container;

  public TitleDialog(Context context, LayoutInflater inflater) {
    this.binding = HeadViewBinding.inflate(inflater);
    this.container = new LinearLayout(context);
    this.setContainer();
    this.setLayoutParam();
  }

  /** Method for setting up a class container */
  private void setContainer() {
    this.container.setOrientation(LinearLayout.VERTICAL);
    this.container.addView(binding.getRoot());
  }

  public void setLayoutParam() {
    this.LP_DEFAULT =
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    this.LP_DEFAULT.setMargins(60, 10, 60, 20);
  }

  /**
   * Method for setting header text uiDialog.setHeadTextView(string);
   *
   * @param headString - Dialog box title
   */
  public final void setHeadTextView(String headString) {
    this.binding.HeadTextDialog.setText(headString);
  }

  /**
   * The method that returns container Here you need to add container filling
   * builder.setView(uiDialog.getContainer());
   *
   * @return - container
   */
  public final LinearLayout getContainer() {
    return this.container;
  }

  /** method to set font size for message */
  public void setTextSizeMessage(TextView textView) {
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
  }
}
