package com.pasich.mynotes.utils.prefences;

import android.widget.ImageButton;

import com.pasich.mynotes.R;
import com.preference.PowerPreference;

public class TextStylePreferences {

    private final ImageButton mButton;
    public static final String ARGUMENT_PREFERENCE_TEXT_STYLE = "textStyle";
    public static final String ARGUMENT_DEFAULT_TEXT_STYLE = "normal";


    public TextStylePreferences(ImageButton button) {
        this.mButton = button;
        mButton.setImageResource(getLoadSrcDrawable(getArgPreference()));
    }

    private String getArgPreference() {
        return PowerPreference.getDefaultFile().getString(ARGUMENT_PREFERENCE_TEXT_STYLE, ARGUMENT_DEFAULT_TEXT_STYLE);
    }


    public void changeArgument() {
        switch (getArgPreference()) {
            case ARGUMENT_DEFAULT_TEXT_STYLE:
                //selected italic
                mButton.setImageResource(getLoadSrcDrawable("italic"));
                PowerPreference.getDefaultFile().setString(ARGUMENT_PREFERENCE_TEXT_STYLE, "italic");
                break;
            case "italic":
                //selected bold
                mButton.setImageResource(getLoadSrcDrawable("bold"));
                PowerPreference.getDefaultFile().setString(ARGUMENT_PREFERENCE_TEXT_STYLE, "bold");
                break;
            case "bold":
                //selected normal
                mButton.setImageResource(getLoadSrcDrawable("normal"));
                PowerPreference.getDefaultFile().setString(ARGUMENT_PREFERENCE_TEXT_STYLE, ARGUMENT_DEFAULT_TEXT_STYLE);
                break;
        }
    }


    private int getLoadSrcDrawable(String param) {
        int NORMAL_ICON = R.drawable.ic_style_normal;
        int ITALIC_ICON = R.drawable.ic_style_italic;
        int BOLD_ICON = R.drawable.ic_style_bold;
        switch (param) {
            case "italic":
                return ITALIC_ICON;
            case "bold":
                return BOLD_ICON;
            default:
                return NORMAL_ICON;
        }

    }
}
