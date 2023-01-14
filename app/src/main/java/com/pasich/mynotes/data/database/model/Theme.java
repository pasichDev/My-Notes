package com.pasich.mynotes.data.database.model;

public class Theme {

    private final int Image;
    private boolean check;
    private final int THEME_ID;
    private final int THEME_STYLE;
    private final int demoLogo;

    public Theme(int Image, int theme_id, int theme_style, int demoLogo) {
        this.Image = Image;
        this.check = false;
        this.THEME_ID = theme_id;
        this.THEME_STYLE = theme_style;
        this.demoLogo = demoLogo;
    }

    public int getTHEME_STYLE() {
        return THEME_STYLE;
    }

    public int getId() {
        return THEME_ID;
    }

    public int getImage() {
        return Image;
    }

    public boolean isCheck() {
        return check;
    }

    public int getDemoLogo() {
        return demoLogo;
    }

    public Theme setCheckReturn(boolean check) {
        this.check = check;
        return this;
    }
}
