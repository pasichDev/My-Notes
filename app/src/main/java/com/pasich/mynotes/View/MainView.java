package com.pasich.mynotes.View;

import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.Controllers.Activity.MainActivity;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.SpacesItemDecoration;

public class MainView extends MainActivity {

  public final Toolbar toolbar;
  public final TabLayout TabLayout;
  public final RecyclerView ListView;
  public final ImageButton sortButton, formatButton;
  public final ImageButton newNotesButton, deleteTag;
  private final View view;

  public MainView(View rootView) {
    this.view = rootView;
    this.toolbar = rootView.findViewById(R.id.toolbar_actionbar);
    this.TabLayout = rootView.findViewById(R.id.Tags);
    this.ListView = rootView.findViewById(R.id.list_notes);
    this.newNotesButton = rootView.findViewById(R.id.newNotesButton);
    this.sortButton = rootView.findViewById(R.id.sortButton);
    this.formatButton = rootView.findViewById(R.id.formatButton);
    this.deleteTag = rootView.findViewById(R.id.deleteTag);

    initialization();
  }

  private void initialization() {
    ListView.addItemDecoration(new SpacesItemDecoration(25));
    setNotesListCountColumns();
  }

  /** Method that changes the number of GridView columns */
  public void setNotesListCountColumns() {
    ListView.setLayoutManager(
        new StaggeredGridLayoutManager(
            PreferenceManager.getDefaultSharedPreferences(view.getContext())
                .getInt("formatParam", 1),
            LinearLayoutManager.VERTICAL));
  }
}
