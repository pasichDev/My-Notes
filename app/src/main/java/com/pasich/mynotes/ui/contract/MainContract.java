package com.pasich.mynotes.ui.contract;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.base.view.MainSortView;
import com.pasich.mynotes.base.view.MoreNoteMainActivityView;
import com.pasich.mynotes.base.view.RestoreNotesBackupOld;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.utils.actionPanel.interfaces.ManagerViewAction;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public interface MainContract {

    interface view extends BaseView, MoreNoteMainActivityView, MainSortView, ManagerViewAction<Note>, RestoreNotesBackupOld {
        void settingsSearchView();

        void settingsNotesList();

        void newNotesButton();

        void moreActivity();

        void startCreateTagDialog();

        void choiceTagDialog(Tag tag);

        void choiceNoteDialog(Note note, int position);

        void settingsTagsList();

        void selectTagUser(int position);

        void loadingData(Flowable<List<Tag>> tagList, Flowable<List<Note>> noteList, String sortParam);

        void openNoteEdit(int idNote);

        void startToastCheckCountTags();

        void sortButton();

        void formatButton();

        void startSearchDialog();
    }


    @PerActivity
    interface presenter extends BasePresenter<view> {
        void newNotesClick();

        void moreActivityClick();

        void clickTag(Tag tag, int position);

        void clickLongTag(Tag tag);

        void clickNote(int idNote);

        void deleteNotesArray(ArrayList<Note> notes);

        void addNote(Note note);

        void sortButton();

        void formatButton();

        void startSearchDialog();

        void deleteNote(Note note);

        void restoreNote(Note nNote);
    }
}
