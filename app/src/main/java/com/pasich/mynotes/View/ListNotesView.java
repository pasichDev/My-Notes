package com.pasich.mynotes.View;

import android.view.View;
import android.widget.GridView;

import androidx.preference.PreferenceManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Constants.SystemConstant;

public class ListNotesView {

  protected final View view;
  public GridView NotesList;

  public ListNotesView(View rootView) {
    this.view = rootView;
    this.NotesList = view.findViewById(R.id.ListFileNotes);
    setNotesListCountColumns();
  }

  /**
   * Method that changes the number of GridView columns
   */
  public void setNotesListCountColumns() {
    NotesList.setNumColumns(
        PreferenceManager.getDefaultSharedPreferences(view.getContext())
            .getInt("formatParam", SystemConstant.Setting_Format));
  }
}
