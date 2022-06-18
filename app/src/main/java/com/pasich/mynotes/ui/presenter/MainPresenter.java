package com.pasich.mynotes.ui.presenter;

import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.ui.contract.MainContract;

public class MainPresenter extends PresenterBase<MainContract.view>
    implements MainContract.presenter {

  private DataManager data;

  public MainPresenter() {}

  @Override
  public void setDataManager(DataManager dataManager) {
    data = dataManager;
  }

  @Override
  public void viewIsReady() {
    getView().settingsSearchView();
    getView().settingsTagsList(data.getTagsRepository().getTags());
    getView().initListeners();
    getView().settingsNotesList(getFormatParam());

  }

  private int getFormatParam() {
    return data.getDefaultPreference().getInt("formatParam", 1);
  }

  @Override
  public void detachView() {}

  @Override
  public void destroy() {

    data.getTagsRepository().destroyInstance();
    data = null;
  }

  @Override
  public void newNotesClick() {
    if (isViewAttached()) getView().newNotesButton();
  }

  @Override
  public void moreActivityClick() {
    if (isViewAttached()) getView().moreActivity();

  }

  @Override
  public void addTag(String nameTag) {
    if (isViewAttached()) data.getTagsRepository().insert(new Tag().create(nameTag));
  }

  @Override
  public void clickTag(Tag tag) {
    if (tag.getSystemAction() == 1) {
      getView().startCreateTagDialog();
    } else {
      /*   TagListAdapter.chooseTag(position);
      restartListNotes(
              TagListAdapter.getCheckedPosition() == 1
                      ? ""
                      : MainModel.tagsArray.get(TagListAdapter.getCheckedPosition()).getNameTag());*/
    }
  }

  @Override
  public void clickLongTag(Tag tag) {
    String[] keysNote = {
      String.valueOf(0),
      tag.getNameTag(),
      /*  Здесь вместо 1 нужно прописать количество заметок с данным тегом
      data.getTagsRepository.getCountNotesTags(tag.getNameTag());
      String.valueOf(
                MainModel.getCountNotesTag(tag.getNameTag()))*/
      String.valueOf(1)
    };
    getView().choiceTagDialog(keysNote);
  }
}
