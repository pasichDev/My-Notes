package com.pasich.mynotes.ui.view.dialogs.main.ChoiceTagDialog;

import android.view.LayoutInflater;

import androidx.appcompat.widget.SwitchCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ViewInfoItemBinding;
import com.pasich.mynotes.databinding.ViewVisibilityTagBinding;
import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;

public class ChoiceTagView extends ListDialogView {

  private final ViewVisibilityTagBinding ViewVisibilitySwitch;
  private final ViewInfoItemBinding ViewInfoItem;

  public ChoiceTagView(LayoutInflater Inflater) {
    super(Inflater);
    this.ViewVisibilitySwitch = ViewVisibilityTagBinding.inflate(Inflater);
    this.ViewInfoItem = ViewInfoItemBinding.inflate(Inflater);

    addTitle("");
    addView(ViewVisibilitySwitch.getRoot());
    addView(getItemsView());
  }

  public void initializeInfoLayout(int countNotesToTag) {
    ViewInfoItem.infoTextView.setText(
        getContextRoot().getString(R.string.layoutStringInfoTags, String.valueOf(countNotesToTag)));
    addView(ViewInfoItem.getRoot());
  }

  public SwitchCompat getSwitchVisibility() {
    return this.ViewVisibilitySwitch.switchVisibilityTag;
  }
}
