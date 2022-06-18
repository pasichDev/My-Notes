package com.pasich.mynotes.ui.contract;

import androidx.lifecycle.LiveData;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.view.MyView;
import com.pasich.mynotes.base.view.NotesListView;
import com.pasich.mynotes.base.view.TagView;
import com.pasich.mynotes.data.tags.Tag;

import java.util.List;

public interface MainContract {

  interface view extends MyView, TagView, NotesListView {
    void settingsSearchView();

    void newNotesButton();

    void moreActivity();

    void startCreateTagDialog();

    void choiceTagDialog(Tag tag, String[] arg);

    void settingsTagsList(LiveData<List<Tag>> tagList);

    void selectTagUser(int position);
  }

  interface presenter extends MyPresenter<view> {
    void newNotesClick();

    void moreActivityClick();

    void addTag(String nameTag);

    void deleteTag(Tag tag);

    void editVisibility(Tag tag);

    void clickTag(Tag tag, int position);

    void clickLongTag(Tag tag);

  }
}
