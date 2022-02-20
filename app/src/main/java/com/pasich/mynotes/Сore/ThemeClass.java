package com.pasich.mynotes.Сore;

import com.pasich.mynotes.R;

public class ThemeClass {



    //Метод изменения цвета цветовой схемы
    public static int  ThemeColorValue (String themeValue){
        int theme = 0;
        if(themeValue == null && themeValue.trim().isEmpty()){  theme =  R.style.ThemeDark;}
        if(themeValue.equals("Red"))  theme =  R.style.ThemeRed;
        if(themeValue.equals("Purple"))  theme =  R.style.ThemePurple;
        if(themeValue.equals("Green"))  theme =  R.style.ThemeGreen;
        if(themeValue.equals("Dark"))  theme =  R.style.ThemeDark;
        if(themeValue.equals("Orange"))  theme =  R.style.ThemeOrange;
        if(themeValue.equals("Indigo"))  theme =  R.style.ThemeIndigo;
        return theme;
    }

}
