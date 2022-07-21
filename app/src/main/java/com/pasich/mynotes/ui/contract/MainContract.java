package com.pasich.mynotes.ui.contract;

import androidx.lifecycle.LiveData;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.view.MyView;
import com.pasich.mynotes.base.view.NoteView;
import com.pasich.mynotes.base.view.TagView;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;

import java.util.List;

public interface MainContract {

  interface view extends MyView, TagView, NoteView {
    void settingsSearchView();

    void newNotesButton();

    void moreActivity();

    void startCreateTagDialog();

    void choiceTagDialog(Tag tag, Integer[] arg);

    void choiceNoteDialog(Note note);

    void settingsTagsList(LiveData<List<Tag>> tagList);
    void selectTagUser(int position);

  }

  interface presenter extends MyPresenter<view> {
    void newNotesClick();

    void moreActivityClick();

    void addTag(String nameTag);

    void deleteTag(Tag tag, boolean deleteNotes);

    void editVisibility(Tag tag);

    void clickTag(Tag tag, int position);

    void clickLongTag(Tag tag);

    void clickLongNote(Note note);

    void deleteNote(Note note);


    void editTagNote(Tag tag, Note note);

      LiveData<List<Tag>> getTagsArray();
  }
}
