package com.pasich.mynotes.ui.presenter;

import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.ui.contract.MainContract;

public class MainPresenter extends PresenterBase<MainContract.view>
    implements MainContract.presenter {
  public MainPresenter() {}

  private DataManager dataManager;

  @Override
  public void viewIsReady() {
    getView().settingsSearchView();
    getView().settingsTagsList();
    getView().setEmptyListNotes();

    dataManager = getView().getDataManager();
    getView().settingsNotesList(dataManager.getDefaultPreference().getInt("formatParam", 1));
  }

  @Override
  public void detachView() {}

  @Override
  public void destroy() {}

  @Override
  public void newNotesClick() {
    if (isViewAttached()) getView().newNotesButton();
  }

  @Override
  public void moreActivityClick() {
    if (isViewAttached()) getView().moreActivity();
  }
}
