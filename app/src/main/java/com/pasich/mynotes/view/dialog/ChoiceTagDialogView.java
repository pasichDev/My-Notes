package com.pasich.mynotes.view.dialog;

import android.view.LayoutInflater;

import androidx.appcompat.widget.SwitchCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ViewInfoItemBinding;
import com.pasich.mynotes.databinding.ViewVisibilityTagBinding;
import com.pasich.mynotes.view.ListDialogView;

public class ChoiceTagDialogView extends ListDialogView {

  private final ViewVisibilityTagBinding ViewVisibilitySwitch;
  private final ViewInfoItemBinding ViewInfoItem;

  public ChoiceTagDialogView(LayoutInflater Inflater) {
    super(Inflater);
    this.ViewVisibilitySwitch = ViewVisibilityTagBinding.inflate(Inflater);
    this.ViewInfoItem = ViewInfoItemBinding.inflate(Inflater);

    addTitle("");
    getRootContainer().addView(ViewVisibilitySwitch.getRoot());
    getRootContainer().addView(getItemsView());
  }

  public void initializeInfoLayout(String countNotesToTag) {
    ViewInfoItem.infoTextView.setText(
        getContextRoot().getString(R.string.layoutStringInfoTags, countNotesToTag));
    getRootContainer().addView(ViewInfoItem.getRoot());
  }

  public SwitchCompat getSwitchVisibility() {
    return this.ViewVisibilitySwitch.switchVisibilityTag;
  }
}
