package com.pasich.mynotes.utils;


import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.notes.Note;

/**
 * Класс который в будующем нужно переписать, чтобы можно было делиться одной заметкой
 * или несколькоми
 */
@Deprecated
public class ShareUtils {

    private Note note;
    private String valueString;
    private Activity activity;

    public ShareUtils(Note note, Activity activity) {
        this.note = note;
        this.valueString = "";
        this.activity = activity;
    }

    public ShareUtils(String value, Activity activity) {
        this.valueString = value;
        this.note = null;
        this.activity = activity;
    }


    /**
     * Method for calling the share note window
     */
    public void shareNotes() {
        String value = note.getValue();
        if (!(value.length() == 0)) {
            activity.startActivity(
                    Intent.createChooser(
                            new Intent("android.intent.action.SEND")
                                    .setType("plain/text")
                                    .putExtra("android.intent.extra.TEXT", value),
                            activity.getString(R.string.share)));

        } else {
            Toast.makeText(activity, R.string.shareNull, Toast.LENGTH_SHORT).show();
        }
        closeUtils();
    }

    /**
     * Method for calling the share app window
     */
    public void shareText() {
        if (!(valueString.length() == 0)) {
            activity.startActivity(
                    Intent.createChooser(
                            new Intent("android.intent.action.SEND")
                                    .setType("plain/text")
                                    .putExtra("android.intent.extra.TEXT", valueString),
                            activity.getString(R.string.share)));

        }
        closeUtils();
    }

    /**
     * Clean variables utils
     */
    private void closeUtils() {
        valueString = null;
        note = null;
        activity = null;
    }

}
