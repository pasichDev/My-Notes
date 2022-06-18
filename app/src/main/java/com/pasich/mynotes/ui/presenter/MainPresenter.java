package com.pasich.mynotes.ui.presenter;

import android.util.Log;

import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.ui.contract.MainContract;

public class MainPresenter extends PresenterBase<MainContract.view>
    implements MainContract.presenter {

  private DataManager dataManager;
  public MainPresenter() {}

  @Override
  public void viewIsReady() {
    dataManager = getView().getDataManager();
    getView().settingsSearchView();
    getView().settingsTagsList(dataManager.getTagsRepository().getTags());
    getView().initListeners();
    getView().settingsNotesList(getFormatParam());

  }

  private int getFormatParam() {
    return dataManager.getDefaultPreference().getInt("formatParam", 1);
  }

  @Override
  public void detachView() {}

  @Override
  public void destroy() {

    dataManager.getTagsRepository().destroyInstance();
    dataManager = null;
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
    Tag tag = new Tag();
    tag.create(nameTag);
    dataManager.getTagsRepository().insert(tag);
  }

  @Override
  public void clickTag(Tag tag) {
    Log.wtf("pasic", "clickTag: " + tag.getSystemAction());
    /* Tag tagC = new Tag();
        tagC.create("history1",1, true);
        dataManager.getTagsRepository().insert(tagC);
    */
    //  dataManager.getTagsRepository().deleteTag(tag);
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
}
