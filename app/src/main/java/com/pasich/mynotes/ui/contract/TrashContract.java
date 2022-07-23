package com.pasich.mynotes.ui.contract;


import androidx.lifecycle.LiveData;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.view.MyView;
import com.pasich.mynotes.data.trash.TrashNote;

import java.util.List;


public interface TrashContract {

  interface view extends MyView {

  void settingsNotesList(int countColumn, LiveData<List<TrashNote>> noteList);
    void cleanTrashDialogShow();
  }

  interface presenter extends MyPresenter<view> {
   void cleanTrashDialogStart();
  }
}
