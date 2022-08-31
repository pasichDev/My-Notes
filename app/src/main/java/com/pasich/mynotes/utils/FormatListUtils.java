package com.pasich.mynotes.utils;

import android.widget.ImageButton;

import com.pasich.mynotes.R;
import com.preference.PowerPreference;

public class FormatListUtils {
    private ImageButton buttonFormat;

    public void init(ImageButton button) {
        this.buttonFormat = button;
        buttonFormat.setImageResource(getParamIco(getParamFormatValue()));
    }

    private int getParamFormatValue() {
        return PowerPreference.getDefaultFile().getInt("formatParam", 1);
    }

    /**
     * The switch itself, which switches the operating mode depending on the selected parameter
     */
    public void formatNote() {
        switch (getParamFormatValue()) {
            case 1:
                PowerPreference.getDefaultFile().setInt("formatParam", 2);
                buttonFormat.setImageResource(getParamIco(2));
                break;
            case 2:
                PowerPreference.getDefaultFile().setInt("formatParam", 1);
                buttonFormat.setImageResource(getParamIco(1));
                break;
        }
    }

    /**
     * Method to return int drawable
     *
     * @param param - The option chosen by the user
     * @return - int drawable
     */
    private int getParamIco(int param) {
        if (param == 2) {
            return R.drawable.ic_edit_format_tiles;
        }
        return R.drawable.ic_edit_format_list;
    }
}
