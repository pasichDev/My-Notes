package com.pasich.mynotes.ui.contract.dialog;

import com.pasich.mynotes.base.BasePresenter;
import com.pasich.mynotes.base.BaseView;

import io.reactivex.Completable;


public interface NewTagDialogContract {

    interface view extends BaseView {

    }

    interface presenter extends BasePresenter<view> {

        Completable saveTag(String nameNewTag);

    }
}
