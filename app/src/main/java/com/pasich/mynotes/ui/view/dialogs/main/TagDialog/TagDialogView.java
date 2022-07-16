package com.pasich.mynotes.ui.view.dialogs.main.TagDialog;

import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.ui.view.customView.InputTagView;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

public class TagDialogView extends InputTagView {

  public final RecyclerView listTags;

  public TagDialogView(LayoutInflater inflater) {
    super(inflater);
    this.listTags = new RecyclerView(getContextRoot());
    initialization();

  }

  private void initialization() {
    addTitle("");
    initializeListTags();
    addView(getNewTagView());
  }

  private void initializeListTags(){
    listTags.addItemDecoration(new SpacesItemDecoration(5));
    listTags.setLayoutManager(
            new LinearLayoutManager(getContextRoot(), RecyclerView.HORIZONTAL, false));
    addView(listTags,LP_DEFAULT);
  }
}
