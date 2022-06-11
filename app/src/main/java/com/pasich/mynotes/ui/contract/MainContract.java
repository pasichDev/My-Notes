package com.pasich.mynotes.ui.contract;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.MyView;

public interface MainContract<V extends MyView> {

  interface view extends MyView {
    void settingsSearchView();
    void settingsTagsList();

    void settingsNotesList(int countColumn);

    void setEmptyListNotes();

    void newNotesButton();
  }

  interface presenter extends MyPresenter<view> {
    void clickTag();
    void longClickTag();

    void newNotes();
  }
}
