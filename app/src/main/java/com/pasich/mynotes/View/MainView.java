package com.pasich.mynotes.View;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.SpacesItemDecoration;

public class MainView {

  public final Toolbar toolbar;
  public final TabLayout TabLayout;
  public final RecyclerView ListView;
  public final ImageButton moreActivityButton;
  public final ImageButton newNotesButton;
  public final SearchView searchView;
  public final View view;
  private final int SORT_BUTTON_ID = R.id.sortButton;
  private final int FORMAT_BUTTON_ID = R.id.formatButton;
  public ImageButton sortButton, formatButton;

  public MainView(View rootView) {
    this.view = rootView;
    this.toolbar = rootView.findViewById(R.id.toolbar_actionbar);
    this.TabLayout = rootView.findViewById(R.id.Tags);
    this.ListView = rootView.findViewById(R.id.list_notes);
    this.newNotesButton = rootView.findViewById(R.id.newNotesButton);
    this.moreActivityButton = rootView.findViewById(R.id.moreActivity);
    this.searchView = rootView.findViewById(R.id.actionSearch);
    initialization();
  }

  protected void initialization() {
    if (this.ListView != null) initListView();
    if (this.searchView != null) initSearchView();
  }

  public void initListView() {
    this.ListView.addItemDecoration(new SpacesItemDecoration(15));
    this.setNotesListCountColumns();
  }

  /** Method that changes the number of GridView columns */
  public void setNotesListCountColumns() {
    ListView.setLayoutManager(
        new StaggeredGridLayoutManager(
            PreferenceManager.getDefaultSharedPreferences(view.getContext())
                .getInt("formatParam", 1),
            LinearLayoutManager.VERTICAL));
  }

  public void initSearchView() {
    searchView.setSubmitButtonEnabled(false);
    LinearLayout llSearchView = (LinearLayout) searchView.getChildAt(0);
    llSearchView.addView(createSortButton());
    llSearchView.addView(createFormatButton());
  }

  private View createSortButton() {
    sortButton = new ImageButton(view.getContext());
    sortButton.setImageResource(R.drawable.ic_sort);
    sortButton.setBackground(null);
    sortButton.setId(SORT_BUTTON_ID);
    return sortButton;
  }

  private View createFormatButton() {
    formatButton = new ImageButton(view.getContext());
    formatButton.setImageResource(R.drawable.ic_edit_format_tiles);
    formatButton.setBackground(null);
    formatButton.setId(FORMAT_BUTTON_ID);
    return formatButton;
  }
}
