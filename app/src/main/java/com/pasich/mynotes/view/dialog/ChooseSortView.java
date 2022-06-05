package com.pasich.mynotes.view.dialog;

import android.view.LayoutInflater;

import com.pasich.mynotes.R;
import com.pasich.mynotes.view.ListDialogView;

public class ChooseSortView extends ListDialogView {

  public ChooseSortView(LayoutInflater inflater) {
    super(inflater);
    addTitle(getContextRoot().getString(R.string.sortHead));
    getRootContainer().addView(getItemsView());
  }
}
