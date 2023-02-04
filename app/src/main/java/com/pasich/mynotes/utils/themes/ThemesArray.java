package com.pasich.mynotes.utils.themes;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.model.Theme;
import com.pasich.mynotes.utils.constants.PreferencesConfig;

import java.util.ArrayList;

public class ThemesArray {

    private final ArrayList<Theme> themes = new ArrayList<>();

    private void initialThemes() {
        themes.add(new Theme(R.drawable.ic_theme_darkblue, 0, R.style.DefaultThemeNoBackground, R.color.darkBlue_theme_no_background_light_primary));
        themes.add(new Theme(R.drawable.ic_theme_green, 1, R.style.GreenThemeNoBackground, R.color.green_theme_no_background_light_primary));
        themes.add(new Theme(R.drawable.ic_theme_red, 2, R.style.RedThemeNoBackground, R.color.red_theme_no_background_light_primary));
        themes.add(new Theme(R.drawable.ic_theme_yellow, 3, R.style.YellowThemeNoBackground, R.color.yellow_theme_no_background_light_primary));
        themes.add(new Theme(R.drawable.ic_theme_pink, 4, R.style.PinkThemeNoBackground, R.color.pink_theme_no_background_light_primary));
    }

    public ArrayList<Theme> getThemes() {
        initialThemes();
        return themes;
    }

    public int getThemeStyle(int themeID) {
        for (Theme theme : getThemes()) {
            if (theme.getId() == themeID) return theme.getTHEME_STYLE();
        }
        return PreferencesConfig.Theme_DEFAULT.getTHEME_STYLE();
    }
}
