package com.pasich.mynotes.ui.contract.dialogs;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;

import java.util.List;

import io.reactivex.Flowable;


public interface MoreNoteDialogContract {

    interface view extends BaseView {
        void setSeekBarValue(int value);

        void loadingTagsOfChips(Flowable<List<Tag>> tagsList);

        void initInterfaces();

        void callableCopyNote(Long newNoteId);
    }

    interface presenter extends BasePresenter<view> {
        void deleteNote(Note note);

        void editSizeText(int value);

        void removeTagNote(int idNote);

        void editTagNote(String nameTag, int idNote);

        void copyNote(Note note, boolean noteActivity);
    }
}
