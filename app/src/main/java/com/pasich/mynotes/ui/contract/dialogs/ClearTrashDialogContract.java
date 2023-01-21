package com.pasich.mynotes.ui.contract.dialogs;

import com.pasich.mynotes.base.view.BasePresenter;
import com.pasich.mynotes.base.view.BaseView;


public interface ClearTrashDialogContract {

    interface view extends BaseView {
    }

    interface presenter extends BasePresenter<view> {
        void clearTrash();
    }
}
