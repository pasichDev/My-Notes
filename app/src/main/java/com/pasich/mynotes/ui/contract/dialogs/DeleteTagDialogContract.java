package com.pasich.mynotes.ui.contract.dialogs;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.model.Tag;


public interface DeleteTagDialogContract {

    interface view extends BaseView {

    }

    interface presenter extends BasePresenter<view> {
        void getLoadCountNotesForTag(String nameTag);

        void deleteTagUnchecked(Tag tag);

        void deleteTagAndNotes(Tag tag);

        int getCountNotesForTag();


    }
}
