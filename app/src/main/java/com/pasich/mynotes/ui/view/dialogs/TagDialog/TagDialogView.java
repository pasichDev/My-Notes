package com.pasich.mynotes.ui.view.dialogs.TagDialog;

import android.view.LayoutInflater;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.otherClasses.view.custom.InputTagView;

public class TagDialogView extends InputTagView {

  public final TabLayout TabLayoutTags;

  public TagDialogView(LayoutInflater inflater) {
    super(inflater);
    this.TabLayoutTags = new TabLayout(getContextRoot());

    initialization();
  }

  private void initialization() {
    addTitle("");
    addView(TabLayoutTags, LP_DEFAULT);
    addView(getNewTagView());
  }
}
