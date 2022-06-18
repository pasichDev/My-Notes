package com.pasich.mynotes.ui.contract;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.MyView;
import com.pasich.mynotes.base.NotesListView;
import com.pasich.mynotes.base.TagView;
import com.pasich.mynotes.data.tags.Tag;

public interface MainContract {

  interface view extends MyView, TagView, NotesListView {
    void settingsSearchView();

    void newNotesButton();

    void moreActivity();

    void startCreateTagDialog();

    void choiceTagDialog(String[] arg);
  }

  interface presenter extends MyPresenter<view> {
    void newNotesClick();

    void moreActivityClick();

    void addTag(String nameTag);

    void clickTag(Tag tag);

    void clickLongTag(Tag tag);
  }
}
