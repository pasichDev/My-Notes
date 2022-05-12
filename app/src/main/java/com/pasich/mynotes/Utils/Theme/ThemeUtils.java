package com.pasich.mynotes.Utils.Theme;

import android.content.Context;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Constants.SystemConstant;


/**
 * Utility to change theme
 */
public class ThemeUtils {


  /**
   * Method that returns the name for setting the theme
   * @param context - this.context
   * @return - name theme (int)
   * Use -> setTheme(applyTheme(this);
   */
  public static int applyTheme(Context context){
   return ThemeColorValue(PreferenceManager
            .getDefaultSharedPreferences(context)
            .getString("themeColor", SystemConstant.Settings_Theme));
  }

  /**
   * Method that iterates over all available options for the topic name
   * @param themeValue - settings check name
   * @return - okay name theme (int)
   */
  public static int ThemeColorValue(String themeValue) {
    int theme = 0;
    if (themeValue == null) {
      assert false;
      if (themeValue.trim().isEmpty()) {
        theme = R.style.ThemeDark;
      }
    }
    if (themeValue.equals("Red")) theme = R.style.ThemeRed;
    if (themeValue.equals("Purple")) theme = R.style.ThemePurple;
    if (themeValue.equals("Green")) theme = R.style.ThemeGreen;
    if (themeValue.equals("Dark")) theme = R.style.ThemeDark;
    if (themeValue.equals("Orange")) theme = R.style.ThemeOrange;
    if (themeValue.equals("Indigo")) theme = R.style.ThemeIndigo;
    return theme;
  }
}
