package com.pasich.mynotes.base.dialog;

import com.pasich.mynotes.data.old.DataManager;

public interface DialogPresenter<V extends BaseViewDialog> {

  void attachView(V mVIew);

  void setDataManager(DataManager dataManager);

  void viewIsReady();

  void detachView();

  void destroy();
}
