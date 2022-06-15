package com.pasich.mynotes.ui.presenter;

import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.TagsDataSource;
import com.pasich.mynotes.ui.contract.MainContract;

import java.util.List;

public class MainPresenter extends PresenterBase<MainContract.view>
    implements MainContract.presenter {

  private DataManager dataManager;

  public MainPresenter() {}

  @Override
  public void viewIsReady() {
    dataManager = getView().getDataManager();
    getView().settingsSearchView();

    dataManager.getTagsRepository().getTags(new TagsCallListener());
    getView().settingsNotesList(getFormatParam());

    getView().setEmptyListNotes();
  }

  private class TagsCallListener implements TagsDataSource.LoadTagsCallback {

    @Override
    public void onTagsLoaded(List<Tag> tags) {
      if (getView() == null) return;
      Tag tagNewTag = new Tag().create("", 1, false);
      //  Tag tagAllTags = new Tag().create(getString(R.string.allNotes), 0, true);

      tags.add(0, tagNewTag);
      getView().settingsTagsList(tags);
    }
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
