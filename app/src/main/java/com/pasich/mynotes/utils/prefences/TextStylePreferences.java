package com.pasich.mynotes.utils.prefences;

import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_TEXT_STYLE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_STYLE;

import android.widget.ImageButton;

import com.pasich.mynotes.R;
import com.pasich.mynotes.di.scope.PerActivity;
import com.preference.PowerPreference;

import javax.inject.Inject;

@PerActivity
public class TextStylePreferences {

    private ImageButton mButton;

    @Inject
    public TextStylePreferences() {
    }

    public void addButton(ImageButton button) {
        this.mButton = button;
        mButton.setImageResource(getLoadSrcDrawable(getArgPreference()));
    }

    private String getArgPreference() {
        return PowerPreference.getDefaultFile().getString(ARGUMENT_PREFERENCE_TEXT_STYLE, ARGUMENT_DEFAULT_TEXT_STYLE);
    }


    public void changeArgument() {
        if (mButton != null) {
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
