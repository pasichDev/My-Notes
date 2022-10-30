package com.pasich.mynotes.base;

import com.pasich.mynotes.data.DataManager;

public interface BasePresenter<V extends BaseView> {

  void attachView(V mVIew);

  void viewIsReady();

  void detachView();

  void destroy();

  DataManager getDataManager();

}
