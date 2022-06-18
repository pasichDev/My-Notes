package com.pasich.mynotes.base;

import com.pasich.mynotes.data.DataManager;

public interface MyPresenter<V extends MyView> {

  void attachView(V mVIew);

  void setDataManager(DataManager dataManager);

  void viewIsReady();

  void detachView();

  void destroy();
}
