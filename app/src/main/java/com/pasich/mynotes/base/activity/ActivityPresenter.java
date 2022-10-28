package com.pasich.mynotes.base.activity;

import com.pasich.mynotes.data.DataManager;
public interface ActivityPresenter<V extends BaseViewActivity> {

  void attachView(V mVIew);

  void viewIsReady();

  void detachView();

  void destroy();

  DataManager getDataManager();

}
