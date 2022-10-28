package com.pasich.mynotes.ui.contract;


import androidx.lifecycle.LiveData;

import com.pasich.mynotes.base.activity.ActivityPresenter;
import com.pasich.mynotes.base.activity.BaseViewActivity;
import com.pasich.mynotes.base.view.ActionBar;
import com.pasich.mynotes.data.database.model.TrashNote;

import java.util.ArrayList;
import java.util.List;


public interface TrashContract {

    interface view extends BaseViewActivity, ActionBar {

        void settingsNotesList(int countColumn, LiveData<List<TrashNote>> noteList);

        void cleanTrashDialogShow();

        void initActionUtils();
    }

    interface presenter extends ActivityPresenter<view> {
        void cleanTrashDialogStart();

        void restoreNotesArray(ArrayList<TrashNote> notes);
    }
}
;