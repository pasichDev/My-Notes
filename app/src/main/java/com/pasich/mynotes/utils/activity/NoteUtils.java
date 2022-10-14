package com.pasich.mynotes.utils.activity;


import android.graphics.Typeface;

import javax.inject.Inject;

public class NoteUtils {


    @Inject
    public NoteUtils() {
    }

    public int getTypeFace(String textStyle) {
        switch (textStyle) {
            case "italic":
                return Typeface.ITALIC;
            case "bold":
                return Typeface.BOLD;
            case "bold-italic":
                return Typeface.BOLD_ITALIC;
            default:
                return Typeface.NORMAL;
        }

    }


}
