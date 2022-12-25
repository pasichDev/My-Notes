package com.pasich.mynotes.utils.themesUtils;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Theme;

import java.util.ArrayList;

public class ThemesArray {

    private final ArrayList<Theme> themes = new ArrayList<>();

    private void initialThemes() {
        themes.add(new Theme(R.drawable.ic_theme_green, 0, R.style.ThemeGreenNoBackground));
        themes.add(new Theme(R.drawable.ic_theme_darkblue, 1, R.style.ThemeDarkBlueNoBackground));
        themes.add(new Theme(R.drawable.ic_theme_yellow, 2, R.style.ThemeYellowNoBackground));
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
