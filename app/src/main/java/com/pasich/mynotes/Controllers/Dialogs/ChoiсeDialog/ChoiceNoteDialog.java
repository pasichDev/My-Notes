package com.pasich.mynotes.Controllers.Dialogs.ChoiсeDialog;

import static com.pasich.mynotes.Utils.Utils.ShareNotesMethodUtils.shareNotes;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.FileCore;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;

public class ChoiceNoteDialog extends DialogFragment {
  private final String[] arrayKey;
  private UpdateListInterface UpdateListInterface;
  private FileCore fileCore;

  public ChoiceNoteDialog(String[] noteInfo) {
    this.arrayKey = noteInfo;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    UpdateListInterface = (UpdateListInterface) getContext();
    fileCore = new FileCore(getContext());
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

    builder.setItems(
        choiceItems(),
        (dialog, which) -> {
          switch (which) {
            case 0:
              fileCore.transferNotes(arrayKey[1], "trash", arrayKey[2]);
              UpdateListInterface.RemoveItem(Integer.parseInt(arrayKey[0]));
              break;
            case 1:
              shareNotes(getActivity(), fileCore.readFile(arrayKey[1], "").toString());
              break;
            case 2: // Перместить заметку
              /*  CopyNotesDialog copyNotes =
                                new CopyNotesDialog(listNotesfors, defaultListAdapter, selectedItem, pos, folderOutput);
                            assert getFragmentManager() != null;
                            copyNotes.show(getFragmentManager(), "Copy Notes");
              */
              break;
          }
        });

    return builder.create();
  }

  private String[] choiceItems() {
    return !(fileCore.getCountFolder() == 0)
        ? new String[] {
          getString(R.string.trashNotes), getString(R.string.share), getString(R.string.copyNotes)
        }
        : new String[] {getString(R.string.trashNotes), getString(R.string.share)};
  }
}
