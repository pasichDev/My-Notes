package com.pasich.mynotes.ui.view.dialogs.ChoiceNoteDialog;

import android.view.LayoutInflater;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ViewInfoItemBinding;
import com.pasich.mynotes.ui.view.base.ListDialogView;

public class ChoiceNoteView extends ListDialogView {

  private final LayoutInflater Inflater;

  public ChoiceNoteView(LayoutInflater Inflater) {
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
