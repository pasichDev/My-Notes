package com.pasich.mynotes.ui.contract;


import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.view.MyView;

public interface NoteContract {

    interface view extends MyView {

        void settingsEditTextNote(String textStyle);

        void textSizeValueNote(int sizeText);


    }

    interface presenter extends MyPresenter<view> {

    }
}
