package com.pasich.mynotes.ui.contract;


import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.view.ActionBar;
import com.pasich.mynotes.base.view.MyView;
import com.pasich.mynotes.data.notes.Note;

public interface NoteContract {

    interface view extends MyView, ActionBar {

        void settingsEditTextNote(String textStyle);

        void textSizeValueNote(int sizeText);

        void initTypeActivity();

        void createSpeechRecognizer(String speechLanguage, String speechOutput);

        void closeNoteActivity();

        void activatedActivity();

        void loadingNote(Note note);

        void initListenerSpeechRecognizer();


    }

    interface presenter extends MyPresenter<view> {
        void closeActivity();

        void loadingData(int idNote);

        void activateEditNote();

        void createNote(Note note);

        void saveNote(Note note);
    }
}
