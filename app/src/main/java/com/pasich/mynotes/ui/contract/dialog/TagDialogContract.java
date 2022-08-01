package com.pasich.mynotes.ui.contract.dialog;


import androidx.lifecycle.LiveData;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.view.MyView;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;

import java.util.List;


public interface TagDialogContract {

    interface view extends MyView {

        void settingsTagsList(int countColumn, LiveData<List<Tag>> tagsList);
    }

    interface presenter extends MyPresenter<view> {
        void editTagNote(Tag tag, Note note);

        void createTagNote(Tag tag, Note note);

        void removeTagNote(Note note);
    }
}
