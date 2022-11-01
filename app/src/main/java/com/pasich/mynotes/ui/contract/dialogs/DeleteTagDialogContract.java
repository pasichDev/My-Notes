package com.pasich.mynotes.ui.contract.dialogs;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.database.model.Tag;


public interface DeleteTagDialogContract {

    interface view extends BaseView {

    }

    interface presenter extends BasePresenter<view> {
        int getLoadCountNotesForTag(String nameTag);

        void deleteTag(Tag tag, boolean deleteNotes);

    }
}
