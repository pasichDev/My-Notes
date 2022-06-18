package com.pasich.mynotes.ui.view.dialogs.ChoiceTagDialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.otherClasses.models.ada.ChoiceModel;
import com.pasich.mynotes.otherClasses.models.dialog.ChoiceTagDialogModel;
import com.pasich.mynotes.ui.view.dialogs.DeleteTagDialog.DeleteTagDialog;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;

import java.util.ArrayList;

public class ChoiceTagDialog extends BottomSheetDialogFragment {

  private final String[] keysNoteInfo;

  public ChoiceTagDialog(String[] keysNoteInfo) {
    this.keysNoteInfo = keysNoteInfo;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
    final ArrayList<ChoiceModel> arrayChoice = new ArrayList<>();
    final ChoiceTagView view = new ChoiceTagView(getLayoutInflater());
    // final ManageTag ManageTag = (ManageTag) getContext();
    final ChoiceTagDialogModel model = new ChoiceTagDialogModel(getContext(), keysNoteInfo[1]);

    view.setTitle(keysNoteInfo[1]);
    view.initializeInfoLayout(keysNoteInfo[2]);
    view.getSwitchVisibility().setChecked(model.getSwitchVisibilityValue() == 1);

    arrayChoice.add(
        new ChoiceModel(getString(R.string.deleteTag), R.drawable.ic_delete, "deleteTag", false));

    DialogListAdapter adapter = new DialogListAdapter(arrayChoice);
    view.getItemsView().setAdapter(adapter);

    view.getSwitchVisibility()
        .setOnCheckedChangeListener(
            (buttonView, isChecked) -> model.updateVisibilityTag(isChecked));

    view.getItemsView()
        .setOnItemClickListener(
            (parent, v, position, id) -> {
              if (adapter.getItem(position).getAction().equals("deleteTag")) {
                if (Integer.parseInt(keysNoteInfo[2]) == 0) {
                  //        assert ManageTag != null;
                  //       ManageTag.deleteTag(false, Integer.parseInt(keysNoteInfo[0]));
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
