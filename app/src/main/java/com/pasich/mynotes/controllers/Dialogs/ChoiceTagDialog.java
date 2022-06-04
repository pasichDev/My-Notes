package com.pasich.mynotes.controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.MoreListAdapter;
import com.pasich.mynotes.Utils.Interface.ManageTag;
import com.pasich.mynotes.View.DialogView.ChoiceTagDialogView;
import com.pasich.mynotes.models.ChoiceTagDialogModel;
import com.pasich.mynotes.models.adapter.ChoiceModel;

import java.util.ArrayList;

public class ChoiceTagDialog extends DialogFragment {

  private final String[] keysNoteInfo;

  public ChoiceTagDialog(String[] keysNoteInfo) {
    this.keysNoteInfo = keysNoteInfo;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
    final ArrayList<ChoiceModel> arrayChoice = new ArrayList<>();
    final ChoiceTagDialogView view = new ChoiceTagDialogView(requireContext(), getLayoutInflater());
    final ManageTag ManageTag = (ManageTag) getContext();
    final ChoiceTagDialogModel model = new ChoiceTagDialogModel(getContext(), keysNoteInfo[1]);

    view.setHeadTextView(keysNoteInfo[1]);
    view.initializeInfoLayout(keysNoteInfo[2]);
    view.getSwitchVisibilityNotes().setChecked(model.getSwitchVisibilityValue() == 1);

    arrayChoice.add(
        new ChoiceModel(getString(R.string.deleteTag), R.drawable.ic_delete, "deleteTag", false));

    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, arrayChoice);
    view.listView.setAdapter(adapter);

    view.getSwitchVisibilityNotes()
        .setOnCheckedChangeListener(
            (buttonView, isChecked) -> model.updateVisibilityTag(isChecked));

    view.listView.setOnItemClickListener(
        (parent, v, position, id) -> {
          if (adapter.getItem(position).getAction().equals("deleteTag")) {
            if (Integer.parseInt(keysNoteInfo[2]) == 0) {
              assert ManageTag != null;
              ManageTag.deleteTag(false, Integer.parseInt(keysNoteInfo[0]));
            } else {
              new DeleteTagDialog(
                      Integer.parseInt(keysNoteInfo[2]), Integer.parseInt(keysNoteInfo[0]))
                  .show(getParentFragmentManager(), "deleteTag");
            }
          }
          dismiss();
        });

    builder.setContentView(view.getLinearLayout());

    return builder;
  }
}
