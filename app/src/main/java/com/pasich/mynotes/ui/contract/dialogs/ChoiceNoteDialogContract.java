package com.pasich.mynotes.ui.contract.dialogs;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;

import java.util.List;

import io.reactivex.Flowable;


public interface ChoiceNoteDialogContract {

    interface view extends BaseView {
        void loadingTagsOfChips(Flowable<List<Tag>> tagsList);
    }

    interface presenter extends BasePresenter<view> {
        void deleteNote(Note note);

        void removeTagNote(int idNote);

        void editTagNote(String nameTag, int idNote);
    }
}
