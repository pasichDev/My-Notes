package com.pasich.mynotes.View.DialogView;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomHeadUIDialog;

public class EditThemeColorView {

  public final GridView GridView;
  public final TextView textMessage;
  private final Context context;
  public CustomHeadUIDialog uiDialog;

  public EditThemeColorView(Context context, LayoutInflater inflater) {
    this.context = context;
    this.textMessage = new TextView(context);
    this.uiDialog = new CustomHeadUIDialog(context, inflater);
    this.GridView = new GridView(context);

    setGridView();
    setUiDialog();
  }

  /**
   * The method that sets up the GridView
   */
  private void setGridView() {
    GridView.setNumColumns(6);
    GridView.setHorizontalSpacing(10);
  }

  /**
   * Method that sets up the custom dialog
   */
  private void setUiDialog() {
    uiDialog.setHeadTextView(context.getString(R.string.selectColorPrimaryApp));
    uiDialog.getContainer().addView(GridView, uiDialog.LP_DEFAULT);
  }
}
