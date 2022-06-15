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

    dataManager = getView().getDataManager();

    //  dataManager.getTagsRepository().addTag("my phones", 0);
    //   getView().settingsTagsList(dataManager.getTagsRepository().getsTags());
    getView().settingsNotesList(getFormatParam());

    getView().setEmptyListNotes();
  }

  /**
   * Метод который возвращает параметр форматирования списка заметок
   *
   * @return 1 или 2
   */
  private int getFormatParam() {
    return dataManager.getDefaultPreference().getInt("formatParam", 1);
  }

  @Override
  public void detachView() {}

  @Override
  public void destroy() {
    dataManager.getTagsRepository().destroyInstance();
  }

  @Override
  public void newNotesClick() {
    if (isViewAttached()) getView().newNotesButton();
  }

  @Override
  public void moreActivityClick() {
    if (isViewAttached()) getView().moreActivity();
  }
}
