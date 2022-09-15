package com.pasich.mynotes.ui.contract;

import androidx.lifecycle.LiveData;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.view.MainSortView;
import com.pasich.mynotes.base.view.MyView;
import com.pasich.mynotes.base.view.NoteView;
import com.pasich.mynotes.base.view.TagView;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface MainContract {

    interface view extends MyView, TagView, NoteView, MainSortView {
        void settingsSearchView();

        void settingsNotesList(LiveData<List<Note>> noteList);

        void newNotesButton();

        void moreActivity();

        void startCreateTagDialog();

        void choiceTagDialog(Tag tag, Integer[] arg);

        void choiceNoteDialog(Note note, int position);

        void settingsTagsList(LiveData<List<Tag>> tagList);

        void selectTagUser(int position, List<Note> noteListForTag);

        void openNoteEdit(int idNote);

        void startToastCheckCountTags();

        void initActionUtils();

    }

    interface presenter extends MyPresenter<view> {
        void newNotesClick();

        void moreActivityClick();

        void deleteTag(Tag tag, boolean deleteNotes) throws ExecutionException, InterruptedException;

        void editVisibility(Tag tag);

        void clickTag(Tag tag, int position);

        void clickLongTag(Tag tag);

        void clickNote(int idNote);

        void deleteNote(Note note);

        void deleteNotesArray(ArrayList<Note> notes);
    }
}
