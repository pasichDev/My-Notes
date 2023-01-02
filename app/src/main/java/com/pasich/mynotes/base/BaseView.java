package com.pasich.mynotes.base;

import android.view.View;

import androidx.annotation.StringRes;

public interface BaseView {

    void initListeners();

    void selectTheme();

    void showMessage(String message);

    void showMessage(@StringRes int resID);

    void onError(String message, View view);

    void onError(@StringRes int resID, View view);

    default void redrawActivity(int themeStyle) {
    }

    default void vibrateOpenDialog(boolean vibrate) {

    }


}
