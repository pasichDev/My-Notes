package com.pasich.mynotes.Utils.SwitchButton;

import android.content.Context;
import android.widget.ImageButton;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Constants.SystemConstant;

public class FormatSwitchUtils {

  public Context context;
  public ImageButton buttonSort;

  public FormatSwitchUtils(Context context, ImageButton buttonSort) {
    this.context = context;
    this.buttonSort = buttonSort;
  }

  /**
   * The method that requests the parameter that the user has selected
   *
   * @return - formatParam (param)
   */
  protected int getSettingsFormatParam() {
    return PreferenceManager.getDefaultSharedPreferences(context)
        .getInt("formatParam", SystemConstant.Setting_Format);
  }

  /** Method that sets the icon of the button depending on the parameter getSettingsParam() */
  public void getFormatParam() {
    buttonSort.setImageResource(getParamIco(getSettingsFormatParam()));
  }

  /** The switch itself, which switches the operating mode depending on the selected parameter */
  public void formatNote() {
    switch (getSettingsFormatParam()) {
      case 1:
        context
            .getSharedPreferences(
                context.getString(R.string.PreferencesFileName), Context.MODE_PRIVATE)
            .edit()
            .putInt("formatParam", 2)
            .apply();
        buttonSort.setImageResource(getParamIco(2));
        break;
      case 2:
        context
            .getSharedPreferences(
                context.getString(R.string.PreferencesFileName), Context.MODE_PRIVATE)
            .edit()
            .putInt("formatParam", 1)
            .apply();
        buttonSort.setImageResource(getParamIco(1));
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
