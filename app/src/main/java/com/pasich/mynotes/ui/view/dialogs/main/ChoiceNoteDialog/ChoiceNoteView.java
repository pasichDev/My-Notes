package com.pasich.mynotes.ui.view.dialogs.main.ChoiceNoteDialog;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ViewInfoItemBinding;
import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;

public class ChoiceNoteView extends ListDialogView {

  private final LayoutInflater Inflater;

  public ChoiceNoteView(LayoutInflater Inflater) {
    super(Inflater);
    this.Inflater = Inflater;
    addView(getItemsView());
  }

  @SuppressLint("StringFormatMatches")
  public void initializeInfoLayout(String dateNote, int symbolsLength) {
    ViewInfoItemBinding binding = ViewInfoItemBinding.inflate(Inflater);
    binding.infoTextView.setText(
        getContextRoot().getString(R.string.layoutStringInfo, dateNote, symbolsLength));
    addView(binding.getRoot());
  }


}
