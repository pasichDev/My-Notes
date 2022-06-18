package com.pasich.mynotes.ui.view.dialogs.ChoiceTagDialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.TagView;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.otherClasses.models.ada.ChoiceModel;
import com.pasich.mynotes.ui.view.dialogs.DeleteTagDialog.DeleteTagDialog;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;

import java.util.ArrayList;

public class ChoiceTagDialog extends BottomSheetDialogFragment {

  private final String[] keysNoteInfo;
  private final Tag tag;

  public ChoiceTagDialog(Tag tag, String[] keysNoteInfo) {
    this.keysNoteInfo = keysNoteInfo;
    this.tag = tag;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
    final ArrayList<ChoiceModel> arrayChoice = new ArrayList<>();
    final ChoiceTagView view = new ChoiceTagView(getLayoutInflater());
    final TagView tagView = (TagView) getContext();

    view.setTitle(tag.getNameTag());
    view.initializeInfoLayout(keysNoteInfo[2]);
    view.getSwitchVisibility().setChecked(tag.getVisibility() == 1);

    arrayChoice.add(
        new ChoiceModel(getString(R.string.deleteTag), R.drawable.ic_delete, "deleteTag", false));

    final DialogListAdapter adapter = new DialogListAdapter(arrayChoice);
    view.getItemsView().setAdapter(adapter);

    view.getSwitchVisibility()
        .setOnCheckedChangeListener(
            (buttonView, isChecked) -> {
              assert tagView != null;
              tag.setVisibility(isChecked ? 1 : 0);
              tagView.editVisibility(tag);
            });

    view.getItemsView()
        .setOnItemClickListener(
            (parent, v, position, id) -> {
              if (adapter.getItem(position).getAction().equals("deleteTag")) {
                if (Integer.parseInt(keysNoteInfo[2]) == 0) {
                  assert tagView != null;
                  tagView.deleteTag(tag);
                } else {
                  new DeleteTagDialog(
                          Integer.parseInt(keysNoteInfo[2]), Integer.parseInt(keysNoteInfo[0]))
                      .show(getParentFragmentManager(), "deleteTag");
                }
              }
              dismiss();
            });

    builder.setContentView(view.getRootContainer());

    return builder;
  }
}
