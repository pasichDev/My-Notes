package com.pasich.mynotes.ui.contract.dialog;

import com.pasich.mynotes.base.dialog.BaseViewDialog;
import com.pasich.mynotes.base.dialog.DialogPresenter;
import com.pasich.mynotes.data.database.model.Tag;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface TagDialogContract {

    interface view extends BaseViewDialog {
        void initTitle();

        void loadingTagsOfChips(List<Tag> tagsList);
    }

    interface presenter extends DialogPresenter<view> {
        void editTagNote(String nameTag, int noteId);

        int getCountTags() throws ExecutionException, InterruptedException;

        void createTagNote(Tag tag, int noteId);

        void removeTagNote(int noteId);
    }
}
