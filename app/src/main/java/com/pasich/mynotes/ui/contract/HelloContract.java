package com.pasich.mynotes.ui.contract;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.base.view.MainSortView;
import com.pasich.mynotes.base.view.MoreNoteMainActivityView;
import com.pasich.mynotes.base.view.RestoreNotesBackupOld;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.utils.actionPanel.interfaces.ManagerViewAction;

public interface HelloContract {

    interface view extends BaseView, MoreNoteMainActivityView, MainSortView, ManagerViewAction<Note>, RestoreNotesBackupOld {

    }


    @PerActivity
    interface presenter extends BasePresenter<view> {

        void addNote(Note note);

        void addTrashNote(TrashNote note);
    }
}
