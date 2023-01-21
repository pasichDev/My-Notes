package com.pasich.mynotes.ui.contract;

import android.view.View;

import com.pasich.mynotes.base.view.BasePresenter;
import com.pasich.mynotes.base.view.BaseView;
import com.pasich.mynotes.base.view.MainSortView;
import com.pasich.mynotes.base.view.MoreNoteMainActivityView;
import com.pasich.mynotes.base.view.ShortCutView;
import com.pasich.mynotes.data.model.Note;
import com.pasich.mynotes.data.model.Tag;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.utils.actionPanel.interfaces.ManagerViewAction;

import java.util.ArrayList;
import java.util.List;

public interface MainContract {

    interface view extends BaseView, MoreNoteMainActivityView, MainSortView, ManagerViewAction<Note>, ShortCutView {
        void settingsSearchView();

        void settingsNotesList();

        void newNotesButton();

        void moreActivity();

        void startCreateTagDialog();

        void choiceTagDialog(Tag tag, View mView);

        void choiceNoteDialog(Note note, int position);

        void settingsTagsList();

        void selectTagUser(int position);

        void loadingNotes(List<Note> noteList);

        void loadingTags(List<Tag> tagList);

        void openNoteEdit(long idNote);

        void startToastCheckCountTags();

        void sortButton();

        void formatButton();

        void startSearchDialog();

        void startDeleteTagDialog(Tag tag);

        void exitWhat();

        void finishActivityOtPresenter();
    }


    @PerActivity
    interface presenter extends BasePresenter<view> {
        void newNotesClick();

        void moreActivityClick();

        void clickTag(Tag tag, int position);

        void clickLongTag(Tag tag, View mView);

        void clickNote(int idNote);

        void deleteNotesArray(ArrayList<Note> notes);

        void addNote(Note note);

        void sortButton();

        void formatButton();

        void startSearchDialog();

        void deleteNote(Note note);

        void restoreNote(Note nNote);

        void deleteTag(Tag tag);

        void editVisibleTag(Tag tag);

        void loadingData();

        Note getBackupDeleteNote();

        void setBackupDeleteNote(Note backupDeleteNote);

        String getSortParam();

        void closeApp();
    }
}
