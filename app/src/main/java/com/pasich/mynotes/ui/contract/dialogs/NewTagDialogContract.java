package com.pasich.mynotes.ui.contract.dialogs;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.data.model.Tag;


public interface NewTagDialogContract {

    interface view extends BaseView {

    }

    interface presenter extends BasePresenter<view> {

        void saveTag(String nameNewTag);

        void editNameTag(String nameNewTag, Tag mTag);
    }
}
