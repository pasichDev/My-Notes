package com.pasich.mynotes.lib;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasich.mynotes.R;

/**
 * A class that simplifies working with dialogs CustomUIDialog uiDialog = new
 * CustomUIDialog(getContext(), getLayoutInflater());
 * uiDialog.setHeadTextView(getString(R.string.warning)); builder.setView(uiDialog.getContainer());
 */
public class CustomUIDialog {

  protected View convertView;
  protected int headLayout = R.layout.dialog_head_bar,
      headTextView = R.id.textViewHead,
      layoutOrientation = LinearLayout.VERTICAL;
  protected TextView headText;
  protected LinearLayout container;
  protected ImageButton closeBut;
  public LinearLayout.LayoutParams lp;
  public int sizeTextMessage = 17;

  public CustomUIDialog(Context context, LayoutInflater inflater) {
    this.convertView = inflater.inflate(this.headLayout, null);
    this.headText = this.convertView.findViewById(headTextView);
    this.container = new LinearLayout(context);
    this.closeBut = this.convertView.findViewById(R.id.closeDialog);
    this.lp =
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    this.lp.setMargins(70, 40, 70, 20);
    this.setContainer();
  }

  /** Method for setting up a class container */
  private void setContainer() {
    this.container.setOrientation(layoutOrientation); // setOrientation container
    this.container.addView(convertView);
  }

  /**
   * Method for setting header text uiDialog.setHeadTextView(string);
   *
   * @param headString - Dialog box title
   */
  public void setHeadTextView(String headString) {
    this.headText.setText(headString);
  }

  /**
   * The method that returns closeBut
   *
   * @return - closeBut
   */
  public ImageButton getCloseButton() {
    return this.closeBut;
  }

  /**
   * The method that returns container Here you need to add container filling
   * builder.setView(uiDialog.getContainer());
   *
   * @return - container
   */
  public LinearLayout getContainer() {
    return this.container;
  }

  /**
   * method to set font size for message
   */
  public void setTextSizeMessage(TextView textView){
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeTextMessage);
  }

}
