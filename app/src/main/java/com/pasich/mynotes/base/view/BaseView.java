package com.pasich.mynotes.base.view;

import android.view.View;

import androidx.annotation.StringRes;

public interface BaseView {

    void initListeners();

    void selectTheme();

    void showMessage(String message);

    void showMessage(@StringRes int resID);

    void onInfo(String message, View view);

    void onInfo(@StringRes int resID, View view);

    void onError(@StringRes int resID, View view);

    default void redrawActivity(int themeStyle) {
    }

    default void vibrateOpenDialog(boolean vibrate) {

    }

    default boolean isNetworkConnected() {

        return false;
    }

    ;


}
