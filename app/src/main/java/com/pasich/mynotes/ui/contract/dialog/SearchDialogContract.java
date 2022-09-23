package com.pasich.mynotes.ui.contract.dialog;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.view.MyView;
import com.pasich.mynotes.data.notes.Note;

import java.util.List;


public interface SearchDialogContract {

    interface view extends MyView {

        void settingsListResult();

        void createListenerSearch(List<Note> mNotes);
    }

    interface presenter extends MyPresenter<view> {


    }
}
