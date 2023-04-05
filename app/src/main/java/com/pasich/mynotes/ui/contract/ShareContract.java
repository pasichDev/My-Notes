package com.pasich.mynotes.ui.contract;

import com.pasich.mynotes.base.view.BasePresenter;
import com.pasich.mynotes.base.view.BaseView;


public interface ShareContract {

    interface view extends BaseView {

        void openNoteEdition(long idNote);
    }

    interface presenter extends BasePresenter<view> {

        void creteNoteShare(String textNote);
    }
}
