package com.pasich.mynotes.View;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.SpacesItemDecoration;

public class TrashView {

  public final Toolbar toolbar;
  protected final View view;
  public final RecyclerView trashNotesList;

  public TrashView(View rootView) {
    this.view = rootView;
    this.toolbar = view.findViewById(R.id.toolbar_actionbar);
    this.trashNotesList = view.findViewById(R.id.ListTrash);
    initialization();
  }

  private void initialization() {
    trashNotesList.addItemDecoration(new SpacesItemDecoration(25));
    setNotesListCountColumns();
  }

  /** Method that changes the number of GridView columns */
  public void setNotesListCountColumns() {
    trashNotesList.setLayoutManager(
        new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
  }
}
