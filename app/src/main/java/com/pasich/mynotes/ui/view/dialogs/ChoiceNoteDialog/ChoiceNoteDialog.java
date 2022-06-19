package com.pasich.mynotes.ui.view.dialogs.ChoiceNoteDialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.ChoiceModel;
import com.pasich.mynotes.base.view.NoteView;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.utils.ShareNoteUtils;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;

import java.util.ArrayList;

public class ChoiceNoteDialog extends DialogFragment {

  private final Note note;

  public ChoiceNoteDialog(Note note) {
    this.note = note;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
    final ArrayList<ChoiceModel> arrayChoice = new ArrayList<>();
    final ChoiceNoteView view = new ChoiceNoteView(getLayoutInflater());
    final NoteView noteView = (NoteView) getContext();

    view.initializeInfoLayout(note.getDate(), note.getValue().length());

    arrayChoice.add(
        new ChoiceModel(
            getString(R.string.selectAll), R.drawable.ic_check_box, "SelectAll", false));
    arrayChoice.add(
        new ChoiceModel(getString(R.string.share), R.drawable.ic_share, "Share", false));
    arrayChoice.add(new ChoiceModel(getString(R.string.tag), R.drawable.ic_tag, "Tag", false));
    arrayChoice.add(
        new ChoiceModel(getString(R.string.trashNotes), R.drawable.ic_delete, "Delete", false));

    DialogListAdapter adapter = new DialogListAdapter(arrayChoice);
    view.getItemsView().setAdapter(adapter);

    view.getItemsView()
        .setOnItemClickListener(
            (parent, v, position, id) -> {
              if (adapter.getItem(position).getAction().equals("SelectAll")) {
                //        assert ChoiceNoteInterface != null;
                //        ChoiceNoteInterface.actionNote(Integer.parseInt(keysNoteInfo[0]));
              }
              if (adapter.getItem(position).getAction().equals("Share")) {
                new ShareNoteUtils(note, getActivity()).shareNotes();
              }

              if (adapter.getItem(position).getAction().equals("Tag")) {
                //       new TagDialog(Integer.parseInt(keysNoteInfo[1]),
                // Integer.parseInt(keysNoteInfo[0]))
                //              .show(getParentFragmentManager(), "EditDIalog");
              }

              if (adapter.getItem(position).getAction().equals("Delete")) {
                assert noteView != null;
                noteView.deleteNote(note);
              }

              dismiss();
            });

    builder.setContentView(view.getRootContainer());

    return builder;
  }
}
