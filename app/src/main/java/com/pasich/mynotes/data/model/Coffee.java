package com.pasich.mynotes.data.model;

import android.graphics.drawable.Drawable;

public class Coffee {

    private String Title;
    private Drawable Image;
    private double Price;

    public Coffee create(String Title, Drawable Image, double Price) {
        this.Title = Title;
        this.Image = Image;
        this.Price = Price;
        return this;
    }

    public Drawable getImage() {
        return Image;
    }

    public String getTitle() {
        return Title;
    }

    public void setImage(Drawable image) {
        Image = image;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }
}
