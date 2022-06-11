package com.pasich.mynotes.utils.other;

import android.widget.ImageButton;

import com.pasich.mynotes.R;

public class FormatListUtils {
  private ImageButton buttonFormat;

  public void init(ImageButton button) {
    this.buttonFormat = button;
    getFormatParam();

  }

  /** Method that sets the icon of the button depending on the parameter getSettingsParam() */
  public void getFormatParam() {
    buttonFormat.setImageResource(getParamIco(1));
  }

  /** The switch itself, which switches the operating mode depending on the selected parameter */
  public void formatNote() {
    switch (1) {
      case 1:
        /*  context
        .getSharedPreferences(
            context.getString(R.string.PreferencesFileName), Context.MODE_PRIVATE)
        .edit()
        .putInt("formatParam", 2)
        .apply();*/
        buttonFormat.setImageResource(getParamIco(2));
        break;
      case 2:
        /*    context
        .getSharedPreferences(
            context.getString(R.string.PreferencesFileName), Context.MODE_PRIVATE)
        .edit()
        .putInt("formatParam", 1)
        .apply();*/
        buttonFormat.setImageResource(getParamIco(1));
        break;
    }
  }

  /**
   * Method to return int drawable
   *
   * @param param - The option chosen by the user
   * @return - int drawable
   */
  protected int getParamIco(int param) {
    if (param == 2) {
      return R.drawable.ic_edit_format_tiles;
    }
    return R.drawable.ic_edit_format_list;
  }
}
