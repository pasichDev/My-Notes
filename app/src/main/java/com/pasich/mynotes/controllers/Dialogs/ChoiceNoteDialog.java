package com.pasich.mynotes.controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.MoreListAdapter;
import com.pasich.mynotes.Utils.Interface.ChoiceNoteInterface;
import com.pasich.mynotes.View.DialogView.ChoiceNoteDialogView;
import com.pasich.mynotes.models.adapter.MoreChoiceModel;

import java.util.ArrayList;

public class ChoiceNoteDialog extends DialogFragment {

  private final String[] keysNoteInfo;

  public ChoiceNoteDialog(String[] keysNoteInfo) {
    this.keysNoteInfo = keysNoteInfo;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
    final ArrayList<MoreChoiceModel> arrayChoice = new ArrayList<>();
    final ChoiceNoteDialogView view =
        new ChoiceNoteDialogView(requireContext(), getLayoutInflater());
    final ChoiceNoteInterface ChoiceNoteInterface = (ChoiceNoteInterface) getContext();

    view.initializeInfoLayout(keysNoteInfo[2], keysNoteInfo[3]);

    arrayChoice.add(
        new MoreChoiceModel(
            getString(R.string.selectAll), R.drawable.ic_check_box, "SelectAll", false));
    arrayChoice.add(
        new MoreChoiceModel(getString(R.string.share), R.drawable.ic_share, "Share", false));
    arrayChoice.add(new MoreChoiceModel(getString(R.string.tag), R.drawable.ic_tag, "Tag", false));
    arrayChoice.add(
        new MoreChoiceModel(getString(R.string.trashNotes), R.drawable.ic_delete, "Delete", false));

    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, arrayChoice);
    view.listView.setAdapter(adapter);

    view.listView.setOnItemClickListener(
        (parent, v, position, id) -> {
          if (adapter.getItem(position).getAction().equals("Share")) {
            assert ChoiceNoteInterface != null;
            ChoiceNoteInterface.shareNote(Integer.parseInt(keysNoteInfo[0]));
          }
          if (adapter.getItem(position).getAction().equals("Delete")) {
            assert ChoiceNoteInterface != null;
            ChoiceNoteInterface.deleteNote(
                Integer.parseInt(keysNoteInfo[1]), Integer.parseInt(keysNoteInfo[0]));
          }
          if (adapter.getItem(position).getAction().equals("Tag")) {
            new ChooseTagDialog(
                    Integer.parseInt(keysNoteInfo[1]), Integer.parseInt(keysNoteInfo[0]))
                .show(getParentFragmentManager(), "EditDIalog");
          }
          if (adapter.getItem(position).getAction().equals("SelectAll")) {
            assert ChoiceNoteInterface != null;
            ChoiceNoteInterface.actionNote(Integer.parseInt(keysNoteInfo[0]));
          }

          dismiss();
        });

    builder.setContentView(view.getLinearLayout());

    return builder;
  }
}
