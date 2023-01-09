package com.pasich.mynotes.utils.themes;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Theme;

import java.util.ArrayList;

public class ThemesArray {

    private final ArrayList<Theme> themes = new ArrayList<>();

    private void initialThemes() {

        themes.add(new Theme(R.drawable.ic_theme_darkblue, 0, R.style.DefaultThemeNoBackground, R.drawable.item_theme_check_blue));
        themes.add(new Theme(R.drawable.ic_theme_green, 1, R.style.GreenThemeNoBackground, R.drawable.item_theme_check_green));
        themes.add(new Theme(R.drawable.ic_theme_red, 2, R.style.RedThemeNoBackground, R.drawable.item_theme_check_red));
        themes.add(new Theme(R.drawable.ic_theme_yellow, 3, R.style.YellowThemeNoBackground, R.drawable.item_theme_check_yellow));
        themes.add(new Theme(R.drawable.ic_theme_pink, 4, R.style.PinkThemeNoBackground, R.drawable.item_theme_check_pink));
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
