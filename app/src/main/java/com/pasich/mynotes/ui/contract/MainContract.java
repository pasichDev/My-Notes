package com.pasich.mynotes.ui.contract;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.MyView;

public interface MainContract<V extends MyView> {

  interface view extends MyView {
    void settingsSearchView();
    void addButtonSearchView();
    void settingsTagsList();
    void setEmptyListNotes();
  }

  interface presenter extends MyPresenter<view> {
    void clickTag();

    void longClickTag();

    void loadingDataTagList();
  }
}
