package com.pasich.mynotes.ui.contract;


import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.view.ActionBar;
import com.pasich.mynotes.base.view.ActivitySettings;
import com.pasich.mynotes.base.view.MyView;
import com.pasich.mynotes.base.view.NoteActivityView;
import com.pasich.mynotes.data.notes.Note;

import java.util.concurrent.ExecutionException;

public interface NoteContract {

    interface view extends MyView, ActionBar, NoteActivityView, ActivitySettings {


        void initTypeActivity();

        void createSpeechRecognizer();

        void closeNoteActivity();

        void activatedActivity();

        void loadingNote(Note note);

        void initListenerSpeechRecognizer();

        void loadingSourceNote();


    }

    interface presenter extends MyPresenter<view> {
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
