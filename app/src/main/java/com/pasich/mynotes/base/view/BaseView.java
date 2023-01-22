package com.pasich.mynotes.base.view;

import android.view.View;

import androidx.annotation.StringRes;

public interface BaseView {

    void initListeners();

    void selectTheme();

    void onInfoSnack(@StringRes int resID, View view, int typeInfo, int time);

    default void redrawActivity(int themeStyle) {
    }

    default void vibrateOpenDialog(boolean vibrate) {

    }

    default boolean isNetworkConnected() {

        return false;
    }

}
