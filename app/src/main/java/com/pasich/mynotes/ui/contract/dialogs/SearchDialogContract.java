package com.pasich.mynotes.ui.contract.dialogs;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.database.model.Note;

import java.util.List;

import io.reactivex.Flowable;


public interface SearchDialogContract {

    interface view extends BaseView {

        void settingsListResult();

        void createListenerSearch(Flowable<List<Note>> mNotes);

        void initFabButton();
    }

    interface presenter extends BasePresenter<view> {


    }
}
