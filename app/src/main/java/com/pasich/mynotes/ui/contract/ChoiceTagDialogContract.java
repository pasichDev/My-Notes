package com.pasich.mynotes.ui.contract;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.database.model.Tag;


public interface ChoiceTagDialogContract {

    interface view extends BaseView {

        void startDeleteTagDialog();
    }

    interface presenter extends BasePresenter<view> {
        void getLoadCountNotesForTag(String nameTag);

        void editVisibilityTag(Tag tag);

        void deleteTagInitial(Tag tag);

    }
}
