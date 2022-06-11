package com.pasich.mynotes.base;

public interface MyPresenter<V extends MyView> {

  void attachView(V mVIew);

  void viewIsReady();

  void detachView();

  void destroy();
}
