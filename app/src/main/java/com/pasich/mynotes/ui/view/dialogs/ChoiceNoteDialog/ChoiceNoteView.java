package com.pasich.mynotes.ui.view.dialogs.ChoiceNoteDialog;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ViewInfoItemBinding;
import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

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
