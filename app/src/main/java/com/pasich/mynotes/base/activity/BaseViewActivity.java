package com.pasich.mynotes.base.activity;

import android.view.View;

import androidx.annotation.StringRes;

public interface BaseViewActivity {

  void initListeners();

  void showMessage(String message);

  void showMessage(@StringRes int resID);

  void onError(String message, View view);

  void onError(@StringRes int resID, View view);
}
