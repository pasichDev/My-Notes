package com.pasich.mynotes.base;

import android.view.View;

import androidx.annotation.StringRes;

public interface BaseView {

    void initListeners();

    void showMessage(String message);

    void showMessage(@StringRes int resID);

    void onError(String message, View view);

    void onError(@StringRes int resID, View view);

    void vibrateOpenDialog(boolean vibrate);


}
