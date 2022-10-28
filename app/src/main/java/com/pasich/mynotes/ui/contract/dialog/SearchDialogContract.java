package com.pasich.mynotes.ui.contract.dialog;

import com.pasich.mynotes.base.dialog.BaseViewDialog;
import com.pasich.mynotes.base.dialog.DialogPresenter;
import com.pasich.mynotes.data.database.model.Note;

import java.util.List;


public interface SearchDialogContract {

    interface view extends BaseViewDialog {

        void settingsListResult();

        void createListenerSearch(List<Note> mNotes);

        void initFabButton();
    }

    interface presenter extends DialogPresenter<view> {


    }
}
