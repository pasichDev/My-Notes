package com.pasich.mynotes.ui.contract.dialogs;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.database.model.Note;


public interface MoreNoteDialogContract {

    interface view extends BaseView {
        void setSeekBarValue(int value);

    }

    interface presenter extends BasePresenter<view> {
        void deleteNote(Note note);

        void editSizeText(int value);
    }
}
