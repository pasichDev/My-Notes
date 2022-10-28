package com.pasich.mynotes.base.dialog;


import com.pasich.mynotes.data.DataManager;

public interface DialogPresenter<V extends BaseViewDialog> {

  void attachView(V mVIew);

  void viewIsReady();

  void detachView();

  void destroy();

  DataManager getDataManager();
}
