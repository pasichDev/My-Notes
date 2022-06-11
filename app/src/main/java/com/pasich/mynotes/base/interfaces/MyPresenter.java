package com.pasich.mynotes.base.interfaces;

public interface MyPresenter<V extends MyView> {

  void attachView(V mVIew);

  void viewIsReady();

  void detachView();

  void destroy();
}
