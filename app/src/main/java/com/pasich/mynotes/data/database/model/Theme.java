package com.pasich.mynotes.data.database.model;

public class Theme {

    private final int Image;
    private boolean check;
    private final int THEME_ID;

    public Theme(int Image, int theme_id) {
        this.Image = Image;
        this.check = false;
        this.THEME_ID = theme_id;
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

    public Theme setCheckReturn(boolean check) {
        this.check = check;
        return this;
    }
}
