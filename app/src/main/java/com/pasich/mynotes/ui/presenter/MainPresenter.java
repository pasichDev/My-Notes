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

    // Здесь нужно использовать powerPrefences получения данных
    getView().settingsNotesList(1);
  }

  @Override
  public void detachView() {}

  @Override
  public void destroy() {}




  @Override
  public void clickTag() {

  }

  @Override
  public void newNotes() {
    if (isViewAttached()) getView().newNotesButton();
  }

  @Override
  public void longClickTag() {}
}
