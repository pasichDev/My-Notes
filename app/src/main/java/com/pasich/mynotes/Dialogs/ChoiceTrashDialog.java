package com.pasich.mynotes.Dialogs;

import static com.pasich.mynotes.Utils.Check.CheckEmptyTrashUtils.checkCountListTrash;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;
import com.pasich.mynotes.NoteActivity;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.FileCore;

import java.util.ArrayList;

public class ChoiceTrashDialog extends DialogFragment {
  private final ArrayList<ListNotesModel> listNotesfors;
  private final int pos;
  private final DefaultListAdapter defaultListAdapter;

  public ChoiceTrashDialog(
      int pos, ArrayList<ListNotesModel> ListNotesfors, DefaultListAdapter defaultListAdapter) {
    this.pos = pos;
    this.defaultListAdapter = defaultListAdapter;
    this.listNotesfors = ListNotesfors;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    FileCore fileCore = new FileCore(getContext());
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

    String[] choise = {getString(R.string.OpenViewNotes), getString(R.string.removeNotes)};

    ListNotesModel listNotesfor = (ListNotesModel) listNotesfors.get(pos);
    String selectedItem = listNotesfor.getNameList();

    builder.setItems(
        choise,
        (dialog, which) -> {
          switch (which) {
            case 0:
              Intent intent = new Intent(getActivity(), NoteActivity.class);
              intent.putExtra("KeyFunction", "TrashNote");
              intent.putExtra("idNote", selectedItem);
              startActivityForResult(intent, 1);

              break;
            case 1:
              fileCore.removeNotesFile(selectedItem);
              listNotesfors.remove(pos);
              defaultListAdapter.notifyDataSetChanged();
              if (defaultListAdapter.getCount() == 0) checkCountListTrash(requireActivity());

              break;
          }
        });

    AlertDialog dialog = builder.create();
    dialog.show();
    return dialog;
  }
}
