package com.pasich.mynotes.Controllers.Dialogs.ChoiсeDialog;

import static com.pasich.mynotes.Utils.Utils.ShareNotesMethodUtils.shareNotes;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.CopyFileUtils;
import com.pasich.mynotes.Utils.File.FileCore;
import com.pasich.mynotes.Utils.File.Сount.CountUtils;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;

import java.io.File;

public class ChoiceNoteDialog extends DialogFragment {
  private final String[] arrayKey;
  private UpdateListInterface UpdateListInterface;
  private FileCore fileCore;
  private CountUtils CountUtils;

  public ChoiceNoteDialog(String[] noteInfo) {
    this.arrayKey = noteInfo;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    UpdateListInterface = (UpdateListInterface) getContext();
    fileCore = new FileCore(getContext());
    CountUtils = new CountUtils();
    CopyFileUtils CopyFileUtils =
        new CopyFileUtils(
            new File(requireContext().getFilesDir() + "/" + arrayKey[2], arrayKey[1]),
            new File(requireContext().getFilesDir(), "/trash"));

    builder.setItems(
        choiceItems(),
        (dialog, which) -> {
          switch (which) {
            case 0:
              CopyFileUtils.moveFile();
              UpdateListInterface.RemoveItem(Integer.parseInt(arrayKey[0]));
              break;
            case 1:
              shareNotes(getActivity(), fileCore.readFile(arrayKey[1], arrayKey[2]).toString());
              break;
            case 2:
              if (CountUtils.getCountFolders(requireContext().getFilesDir()) == 0)
                Toast.makeText(
                        getContext(), getString(R.string.error_folders_exists), Toast.LENGTH_LONG)
                    .show();

              break;
          }
        });

    return builder.create();
  }

  private String[] choiceItems() {
    return !(CountUtils.getCountFolders(requireContext().getFilesDir()) == 0)
        ? new String[] {
          getString(R.string.trashNotes), getString(R.string.share), getString(R.string.copyNotes)
        }
        : new String[] {getString(R.string.trashNotes), getString(R.string.share)};
  }
}
