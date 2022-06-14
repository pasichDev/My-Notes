package com.pasich.mynotes.ui.contract;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.MyView;
import com.pasich.mynotes.data.DataManager;

public interface MainContract<V extends MyView> {

  interface view extends MyView {
    void settingsSearchView();
    void settingsTagsList();

    void settingsNotesList(int countColumn);

    void setEmptyListNotes();

    void newNotesButton();

    void moreActivity();

    DataManager getDataManager();
  }

  interface presenter extends MyPresenter<view> {

    void newNotesClick();

    void moreActivityClick();
  }
}
