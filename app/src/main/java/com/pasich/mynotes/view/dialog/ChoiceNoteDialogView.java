package com.pasich.mynotes.view.dialog;

import android.view.LayoutInflater;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ViewInfoItemBinding;
import com.pasich.mynotes.view.base.ListDialogView;

public class ChoiceNoteDialogView extends ListDialogView {

  private final LayoutInflater Inflater;

  public ChoiceNoteDialogView(LayoutInflater Inflater) {
    super(Inflater);
    this.Inflater = Inflater;
    addView(getItemsView());
  }

  public void initializeInfoLayout(String dateNote, String symbolsLength) {
    ViewInfoItemBinding binding = ViewInfoItemBinding.inflate(Inflater);
    binding.infoTextView.setText(
        getContextRoot().getString(R.string.layoutStringInfo, dateNote, symbolsLength));
    addView(binding.getRoot());
  }
}
