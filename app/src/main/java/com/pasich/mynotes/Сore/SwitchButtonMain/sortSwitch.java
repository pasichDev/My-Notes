package com.pasich.mynotes.Сore.SwitchButtonMain;

import static com.pasich.mynotes.Сore.SystemCostant.settingsFileName;

import android.content.Context;
import android.widget.ImageButton;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Сore.SystemCostant;

public class sortSwitch {

  public Context context;
  public ImageButton buttonSort;

  public sortSwitch(Context context, ImageButton buttonSort) {
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
        .getString("sortPref", SystemCostant.Settings_Sort);
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
            .getSharedPreferences(settingsFileName, Context.MODE_PRIVATE)
            .edit()
            .putString("sortPref", "name")
            .apply();
        buttonSort.setImageResource(getParamIco("name"));
        break;
      case "name":
        context
            .getSharedPreferences(settingsFileName, Context.MODE_PRIVATE)
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
