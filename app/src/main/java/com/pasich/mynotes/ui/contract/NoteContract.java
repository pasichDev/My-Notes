package com.pasich.mynotes.ui.contract;


import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.base.view.ActionBar;
import com.pasich.mynotes.base.view.ActivitySettings;
import com.pasich.mynotes.base.view.NoteActivityView;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.utils.permissionManager.AudioPermission;

public interface NoteContract {

    interface view extends BaseView, ActionBar, NoteActivityView, ActivitySettings, AudioPermission {

        void initTypeActivity();

        void closeNoteActivity();

        void activatedActivity();

        void loadingNote(Note note);

        void initListenerSpeechRecognizer();

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
