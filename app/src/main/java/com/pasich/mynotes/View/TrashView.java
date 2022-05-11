package com.pasich.mynotes.View;

import android.view.View;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.pasich.mynotes.R;

public class TrashView {

  public final Toolbar toolbar;
  protected final View view;
  public ListView trashNotesList;

  public TrashView(View rootView) {
    this.view = rootView;
    this.toolbar = view.findViewById(R.id.toolbar_actionbar);
    this.trashNotesList = view.findViewById(R.id.ListTrash);
    setToolbar();
  }

  private void setToolbar() {
    toolbar.setTitle(R.string.trashN);
  }

  /** Method that handles displaying a message about an empty note */
  public void createViewTrashEmpty() {
    view.findViewById(R.id.emptyTrash).setVisibility(View.VISIBLE);
    view.findViewById(R.id.imageEmptyTrash).setVisibility(View.VISIBLE);
  }
}
