package com.pasich.mynotes.ui.presenter;

import com.pasich.mynotes.base.PresenterBase;
import com.pasich.mynotes.ui.contract.TagsContract;

public class TagsPresenter extends PresenterBase<TagsContract.view>
    implements TagsContract.presenter {

  public TagsPresenter() {}

  @Override
  public void viewIsReady() {
    getView().settingsTagsList();
    getView().setEmptyListNotes();
  }

  @Override
  public void detachView() {}

  @Override
  public void destroy() {}

  @Override
  public void clickTag() {}

  @Override
  public void longClickTag() {}

  @Override
  public void loadingDataTagList() {}
}
