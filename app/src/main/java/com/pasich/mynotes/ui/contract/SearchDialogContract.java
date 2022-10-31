package com.pasich.mynotes.ui.contract;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.database.model.Note;

import java.util.List;


public interface SearchDialogContract {

    interface view extends BaseView {

        void settingsListResult();

        void createListenerSearch(List<Note> mNotes);

        void initFabButton();
    }

    interface presenter extends BasePresenter<view> {


    }
}
