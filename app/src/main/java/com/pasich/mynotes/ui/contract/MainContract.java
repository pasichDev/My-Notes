package com.pasich.mynotes.ui.contract;

import androidx.lifecycle.LiveData;

import com.pasich.mynotes.base.activity.ActivityPresenter;
import com.pasich.mynotes.base.activity.BaseViewActivity;
import com.pasich.mynotes.base.view.MainSortView;
import com.pasich.mynotes.base.view.NoteView;
import com.pasich.mynotes.base.view.RestoreNotesBackupOld;
import com.pasich.mynotes.base.view.TagView;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.di.PerActivity;
import com.pasich.mynotes.utils.actionPanel.interfaces.ManagerViewAction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;

public interface MainContract {

    interface view extends BaseViewActivity, TagView, NoteView, MainSortView, ManagerViewAction<Note>, RestoreNotesBackupOld {
        void settingsSearchView();

        void settingsNotesList();

        void newNotesButton();

        void moreActivity();

        void startCreateTagDialog();

        void choiceTagDialog(Tag tag, Integer[] arg);

        void choiceNoteDialog(Note note, int position);

        void settingsTagsList();

        void selectTagUser(int position);

        void loadingData(Observable<List<Tag>> tagList, LiveData<List<Note>> noteList);

        void openNoteEdit(int idNote);

        void startToastCheckCountTags();

        void initActionUtils();

        void sortButton();

        void formatButton();

        void startSearchDialog();
    }


    @PerActivity
    interface presenter extends ActivityPresenter<view> {
        void newNotesClick();

        void moreActivityClick();

        void deleteTag(Tag tag, boolean deleteNotes) throws ExecutionException, InterruptedException;

        void editVisibility(Tag tag);

        void clickTag(Tag tag, int position);

        void clickLongTag(Tag tag);

        void clickNote(int idNote);

        void deleteNote(Note note);

        void deleteNotesArray(ArrayList<Note> notes);

        void addNote(Note note);

        void sortButton();

        void formatButton();

        void startSearchDialog();
    }
}
