package com.pasich.mynotes.data.database.model;

public class Features {

    private int Title;
    private int Image;
    private int Info;
    private boolean button;

    public Features create(int Title, int Image, int Info, boolean button) {
        this.Title = Title;
        this.Image = Image;
        this.Info = Info;
        this.button = button;
        return this;
    }

    public int getImage() {
        return Image;
    }

    public int getTitle() {
        return Title;
    }

    public int getInfo() {
        return Info;
    }

    public void setImage(int image) {
        Image = image;
    }

    public void setTitle(int title) {
        Title = title;
    }

    public void setInfo(int info) {
        Info = info;
    }

    public boolean isButton() {
        return button;
    }
}
