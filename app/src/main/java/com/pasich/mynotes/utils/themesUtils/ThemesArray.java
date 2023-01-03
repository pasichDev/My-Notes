package com.pasich.mynotes.utils.themesUtils;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Theme;

import java.util.ArrayList;

public class ThemesArray {

    private final ArrayList<Theme> themes = new ArrayList<>();

    private void initialThemes() {

        themes.add(new Theme(R.drawable.ic_theme_darkblue, 2, R.style.ThemeDarkBlueNoBackground, R.drawable.item_theme_check_blue));
        themes.add(new Theme(R.drawable.ic_theme_green, 1, R.style.ThemeGreenNoBackground, R.drawable.item_theme_check_green));
        themes.add(new Theme(R.drawable.ic_theme_red, 3, R.style.RedThemeNoBackground, R.drawable.item_theme_check_red));
        themes.add(new Theme(R.drawable.ic_theme_yellow, 4, R.style.ThemeYellowNoBackground, R.drawable.item_theme_check_yellow));
    }

    public ArrayList<Theme> getThemes() {
        initialThemes();
        return themes;
    }

    public int getThemeStyle(int themeID) {
        for (Theme theme : getThemes()) {
            if (theme.getId() == themeID) return theme.getTHEME_STYLE();
        }
        return getThemes().get(0).getTHEME_STYLE();
    }
}
