package com.pasich.mynotes.ui.contract;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.di.scope.PerActivity;

public interface HelloContract {

    interface view extends BaseView {

    }


    @PerActivity
    interface presenter extends BasePresenter<view> {

        void addNote(Note note);

        void addTrashNote(TrashNote note);

        void createTag(Tag tag);
    }
}
