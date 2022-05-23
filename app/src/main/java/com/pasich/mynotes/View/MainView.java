package com.pasich.mynotes.View;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.Controllers.Activity.MainActivity;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Anim.ListViewAnimation;

import java.util.Objects;


public class MainView extends MainActivity {

  public final Toolbar toolbar;
  public final TabLayout TabLayout;
  public final GridView ListView;
  public final ImageButton sortButton, formatButton;
  private final View view;

  public final ImageButton newNotesButton, deleteTag;

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
    ListView.setNumColumns(
        PreferenceManager.getDefaultSharedPreferences(view.getContext()).getInt("formatParam", 1));
    Objects.requireNonNull(TabLayout.getTabAt(1)).select();
    ListViewAnimation.setListviewAnimationLeftToShow(ListView);
  }

  /** Method that changes the number of GridView columns */
  public void setNotesListCountColumns() {
    ListView.setNumColumns(
        PreferenceManager.getDefaultSharedPreferences(view.getContext()).getInt("formatParam", 1));
  }

}
