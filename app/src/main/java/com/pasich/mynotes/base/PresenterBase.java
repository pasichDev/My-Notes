package com.pasich.mynotes.base;

public abstract class PresenterBase<T extends MyView> implements MyPresenter<T> {

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
