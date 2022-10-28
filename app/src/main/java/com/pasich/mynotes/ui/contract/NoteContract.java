package com.pasich.mynotes.ui.contract;


import com.pasich.mynotes.base.activity.ActivityPresenter;
import com.pasich.mynotes.base.activity.BaseViewActivity;
import com.pasich.mynotes.base.view.ActionBar;
import com.pasich.mynotes.base.view.ActivitySettings;
import com.pasich.mynotes.base.view.NoteActivityView;
import com.pasich.mynotes.data.database.notes.Note;
import com.pasich.mynotes.utils.permissionManager.AudioPermission;

import java.util.concurrent.ExecutionException;

public interface NoteContract {

    interface view extends BaseViewActivity, ActionBar, NoteActivityView, ActivitySettings, AudioPermission {


        void initTypeActivity();

        void createSpeechRecognizer();

        void closeNoteActivity();

        void activatedActivity();

        void loadingNote(Note note);

        void initListenerSpeechRecognizer();

        void loadingSourceNote();

        void createActionPanelNote();
    }

    interface presenter extends ActivityPresenter<view> {
        void closeActivity();

        void loadingData(int idNote);

        void activateEditNote();

        long createNote(Note note) throws ExecutionException, InterruptedException;

        void saveNote(Note note);

        void deleteNote(Note note);

        void sourceNote();

        Note loadingNote(int idNote) throws ExecutionException, InterruptedException;
    }
}
