package com.pasich.mynotes.base.activity;

import com.pasich.mynotes.data.DataManger;
public interface ActivityPresenter<V extends BaseViewActivity> {

  void attachView(V mVIew);

  void viewIsReady();

  void detachView();

  void destroy();

  DataManger getDataManager();

}
