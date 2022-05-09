package com.pasich.mynotes.Controllers.Dialogs.ChoiсeDialog;

import static com.pasich.mynotes.Utils.Utils.ShareNotesMethodUtils.shareNotes;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Controllers.Dialogs.CopyNotesDialog;
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
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    UpdateListInterface = (UpdateListInterface) getContext();
    fileCore = new FileCore(getContext());

    builder.setItems(
        choiceItems(),
        (dialog, which) -> {
          switch (which) {
            case 0:
              fileCore.transferNotes(arrayKey[1], "trash", arrayKey[2]);
              UpdateListInterface.RemoveItem(Integer.parseInt(arrayKey[0]));
              break;
            case 1:
              shareNotes(getActivity(), fileCore.readFile(arrayKey[1], arrayKey[2]).toString());
              Log.d("xxxx",arrayKey[2] + "/" + fileCore.readFile(arrayKey[1], arrayKey[2]).toString());
              break;
            case 2:
              if (fileCore.getCountFolder() == 0)
                Toast.makeText(
                        getContext(), getString(R.string.error_folders_exists), Toast.LENGTH_LONG)
                    .show();
              else new CopyNotesDialog(arrayKey).show(getParentFragmentManager(), "Cope Notes");
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
