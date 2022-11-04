package com.pasich.mynotes.ui.contract;


import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.base.view.ActionBar;
import com.pasich.mynotes.base.view.MoreNoteDialogView;
import com.pasich.mynotes.data.database.model.Note;

public interface NoteContract {

    interface view extends BaseView, ActionBar, MoreNoteDialogView {

        void initTypeActivity();

        void closeNoteActivity();

        void activatedActivity();

        void loadingNote(Note note);

        void loadingSourceNote();

        void editIdNoteCreated(long idNote);
    }

    interface presenter extends BasePresenter<view> {
        void closeActivity();

        void loadingData(int idNote);

        void activateEditNote();

        void createNote(Note note);

        void saveNote(Note note);

        void deleteNote(Note note);

        void sourceNote();
    }
}
