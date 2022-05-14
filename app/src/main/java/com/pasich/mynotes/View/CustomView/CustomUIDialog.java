package com.pasich.mynotes.View.CustomView;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
      layoutOrientation = LinearLayout.VERTICAL;
  protected TextView headText;
  protected LinearLayout container;
  private final Button closeButton;
  private final Button saveButton;

  public LinearLayout.LayoutParams lp;
  public int sizeTextMessage = 17;

  public CustomUIDialog(Context context, LayoutInflater inflater) {
    this.convertView = inflater.inflate(this.headLayout, null);
    this.headText = convertView.findViewById(R.id.HeadTextDialog);
    this.closeButton = convertView.findViewById(R.id.closeButtonDialog);
    this.saveButton = convertView.findViewById(R.id.saveButtonDialog);

    this.container = new LinearLayout(context);
    this.lp =
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    this.lp.setMargins(70, 50, 70, 50);
    this.setContainer();
  }

  /** Method for setting up a class container */
  private void setContainer() {
    this.container.setOrientation(layoutOrientation);
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
  public Button getCloseButton() {
    return this.closeButton;
  }

  /**
   * The method that returns save Button
   *
   * @return - save Button
   */
  public Button getSaveButton() {
    return this.saveButton;
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
