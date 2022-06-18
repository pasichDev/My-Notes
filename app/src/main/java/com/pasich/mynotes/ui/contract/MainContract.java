package com.pasich.mynotes.ui.contract;

import androidx.lifecycle.LiveData;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.MyView;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.tags.Tag;

import java.util.List;

public interface MainContract<V extends MyView> {

  interface view extends MyView {
    void settingsSearchView();

    void settingsTagsList(LiveData<List<Tag>> tagList);

    void settingsNotesList(int countColumn);
    void newNotesButton();

    void moreActivity();

    void startCreateTagDialog();

    DataManager getDataManager();
  }

  interface presenter extends MyPresenter<view> {
    void newNotesClick();
    void moreActivityClick();

    void addTag(String nameTag);

    void clickTag(Tag tag);
  }
}
