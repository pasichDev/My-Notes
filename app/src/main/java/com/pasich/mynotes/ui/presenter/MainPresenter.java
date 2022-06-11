package com.pasich.mynotes.ui.presenter;

import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.ui.contract.MainContract;

public class MainPresenter extends PresenterBase<MainContract.view>
    implements MainContract.presenter {

  public MainPresenter() {}

  @Override
  public void viewIsReady() {
    getView().settingsSearchView();
    getView().settingsTagsList();
    getView().setEmptyListNotes();
  }

  @Override
  public void detachView() {}

  @Override
  public void destroy() {}




  @Override
  public void clickTag() {

  }

  @Override
  public void longClickTag() {

  }

  @Override
  public void loadingDataTagList() {

  }
}
