package com.pasich.mynotes.ui.contract;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.di.scope.PerActivity;

import java.util.List;

import io.reactivex.Single;

public interface NoteWidgetConfigureContract {

    interface view extends BaseView {

        void initListNotes();

        void loadingNotes(List<Note> noteList);
    }


    @PerActivity
    interface presenter extends BasePresenter<view> {

        void loadNotes();

        Single loadNote(long idNote);

    }
}
