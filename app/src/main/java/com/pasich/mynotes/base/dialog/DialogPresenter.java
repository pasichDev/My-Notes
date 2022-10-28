package com.pasich.mynotes.base.dialog;


public interface DialogPresenter<V extends BaseViewDialog> {

  void attachView(V mVIew);


  void viewIsReady();

  void detachView();

  void destroy();
}
