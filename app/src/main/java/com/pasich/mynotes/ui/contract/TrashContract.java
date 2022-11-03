package com.pasich.mynotes.ui.contract;


import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.base.view.ActionBar;
import com.pasich.mynotes.data.database.model.TrashNote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;


public interface TrashContract {

    interface view extends BaseView, ActionBar {

        void settingsNotesList(Flowable<List<TrashNote>> noteList);

        void cleanTrashDialogShow();

        void initActionUtils();
    }

    interface presenter extends BasePresenter<view> {
        void cleanTrashDialogStart();

        void restoreNotesArray(ArrayList<TrashNote> notes);
    }
}
;