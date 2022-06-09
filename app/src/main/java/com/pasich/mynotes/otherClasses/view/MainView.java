package com.pasich.mynotes.otherClasses.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.otherClasses.utils.recycler.SpacesItemDecoration;

public class MainView {
  private final ActivityMainBinding binding;

  public ImageButton sortButton, formatButton;

  public MainView(ActivityMainBinding binding) {
    this.binding = binding;
    initialization();
  }

  protected void initialization() {
    initNoteListView();
    initTagsListView();

    initSearchView();
  }

  public void initNoteListView() {
    binding.listNotes.addItemDecoration(new SpacesItemDecoration(15));
    this.setNotesListCountColumns();
  }

  public void initTagsListView() {
    binding.listTags.addItemDecoration(new SpacesItemDecoration(5));
    binding.listTags.setLayoutManager(
        new LinearLayoutManager(binding.getRoot().getContext(), RecyclerView.HORIZONTAL, false));
    this.setNotesListCountColumns();
  }

  /** Method that changes the number of GridView columns */
  public void setNotesListCountColumns() {
    binding.listNotes.setLayoutManager(
        new StaggeredGridLayoutManager(
            PreferenceManager.getDefaultSharedPreferences(binding.getRoot().getContext())
                .getInt("formatParam", 1),
            LinearLayoutManager.VERTICAL));
  }

  public void initSearchView() {
    binding.actionSearch.setSubmitButtonEnabled(false);
    LinearLayout llSearchView = (LinearLayout) binding.actionSearch.getChildAt(0);
    llSearchView.addView(createSortButton());
    llSearchView.addView(createFormatButton());
  }

  private View createSortButton() {
    sortButton = new ImageButton(binding.getRoot().getContext());
    sortButton.setImageResource(R.drawable.ic_sort);
    sortButton.setBackground(null);
    sortButton.setId(R.id.sortButton);
    return sortButton;
  }

  private View createFormatButton() {
    formatButton = new ImageButton(binding.getRoot().getContext());
    formatButton.setImageResource(R.drawable.ic_edit_format_tiles);
    formatButton.setBackground(null);
    formatButton.setId(R.id.formatButton);
    return formatButton;
  }
}
