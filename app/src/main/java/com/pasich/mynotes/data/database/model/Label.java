package com.pasich.mynotes.data.database.model;

public class Label {

    private final boolean check;
    private final int Image;

    public Label(int Image, boolean check) {
        this.Image = Image;
        this.check = check;
    }


    public Label(int Image) {
        this.Image = Image;
        this.check = false;
    }

    public int getImage() {
        return Image;
    }

    public boolean isCheck() {
        return check;
    }
}
