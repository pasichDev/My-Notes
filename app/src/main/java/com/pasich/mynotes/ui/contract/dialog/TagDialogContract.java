package com.pasich.mynotes.ui.contract.dialog;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.view.MyView;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface TagDialogContract {

    interface view extends MyView {
        void initTitle();

        void loadingTagsOfChips(List<Tag> tagsList);
    }

    interface presenter extends MyPresenter<view> {
        void editTagNote(String nameTag, Note note);

        int getCountTags() throws ExecutionException, InterruptedException;

        void createTagNote(Tag tag, Note note);

        void removeTagNote(Note note);
    }
}
