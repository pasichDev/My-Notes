package com.pasich.mynotes.ui.contract.dialogs;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;


public interface NewTagDialogContract {

    interface view extends BaseView {

    }

    interface presenter extends BasePresenter<view> {

        void saveTag(String nameNewTag);
    }
}
