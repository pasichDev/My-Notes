package com.pasich.mynotes.Utils.SwitchButton;


import android.content.Context;
import android.widget.ImageButton;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Constants.SystemConstant;

public class SortSwitchUtils {

  public Context context;
  public ImageButton buttonSort;

  public SortSwitchUtils(Context context, ImageButton buttonSort) {
    this.context = context;
    this.buttonSort = buttonSort;
  }

  /**
   * The method that requests the parameter that the user has selected
   *
   * @return - sortPref (param)
   */
  protected String getSettingsSortParam() {
    return PreferenceManager.getDefaultSharedPreferences(context)
        .getString("sortPref", SystemConstant.Settings_Sort);
  }

  /** Method that sets the icon of the button depending on the parameter getSettingsParam() */
  public void getSortParam() {
    buttonSort.setImageResource(getParamIco(getSettingsSortParam()));
  }

  /** The switch itself, which switches the operating mode depending on the selected parameter */
  public void sortNote() {
    switch (getSettingsSortParam()) {
      case "date":
        context
            .getSharedPreferences(
                context.getString(R.string.PreferencesFileName), Context.MODE_PRIVATE)
            .edit()
            .putString("sortPref", "name")
            .apply();
        buttonSort.setImageResource(getParamIco("name"));
        break;
      case "name":
        context
            .getSharedPreferences(
                context.getString(R.string.PreferencesFileName), Context.MODE_PRIVATE)
            .edit()
            .putString("sortPref", "date")
            .apply();
        buttonSort.setImageResource(getParamIco("date"));
        break;
    }
  }

  /**
   * Method to return int drawable
   *
   * @param param - The option chosen by the user
   * @return - int drawable
   */
  protected int getParamIco(String param) {
    if ("name".equals(param)) {
      return R.drawable.ic_sort_letters;
    }
    return R.drawable.ic_sort_date;
  }
}
