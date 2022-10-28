package com.pasich.mynotes.base.dialog;

public abstract class BasePresenterDialog<T extends BaseViewDialog> implements DialogPresenter<T> {

  private T view;

  @Override
  public void attachView(T v) {
    view = v;
  }

  @Override
  public void detachView() {
    view = null;
  }

  public T getView() {
    return view;
  }

  protected boolean isViewAttached() {
    return view != null;
  }

  @Override
  public void destroy() {
    view = null;
  }


}
