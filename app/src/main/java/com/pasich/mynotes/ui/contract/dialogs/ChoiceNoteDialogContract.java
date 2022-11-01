package com.pasich.mynotes.ui.contract.dialogs;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.database.model.Note;


public interface ChoiceNoteDialogContract {

    interface view extends BaseView {

    }

    interface presenter extends BasePresenter<view> {
        void deleteNote(Note note);
    }
}
