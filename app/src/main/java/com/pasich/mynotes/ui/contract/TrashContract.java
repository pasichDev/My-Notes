package com.pasich.mynotes.ui.contract;


import androidx.lifecycle.LiveData;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.view.ActionBar;
import com.pasich.mynotes.base.view.MyView;
import com.pasich.mynotes.data.trash.TrashNote;

import java.util.List;


public interface TrashContract {

    interface view extends MyView, ActionBar {

        void settingsNotesList(int countColumn, LiveData<List<TrashNote>> noteList);

        void cleanTrashDialogShow();

        void initActionUtils();
    }

    interface presenter extends MyPresenter<view> {
        void cleanTrashDialogStart();
    }
}
;