package com.pasich.mynotes.ui.contract;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.MyView;

public interface TagsContract<V extends MyView> {

  interface view extends MyView {
    void settingsTagsList();

    void setEmptyListNotes();
  }

  interface presenter extends MyPresenter<view> {
    void clickTag();

    void longClickTag();

    void loadingDataTagList();
  }
}
