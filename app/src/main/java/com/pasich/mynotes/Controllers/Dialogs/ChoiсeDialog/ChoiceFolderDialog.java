package com.pasich.mynotes.Controllers.Dialogs.ChoiсeDialog;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Controllers.Dialogs.DeleteFolderDialog;
import com.pasich.mynotes.Controllers.Dialogs.FolderEditAndCreateDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.FolderUtils;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;

import java.io.File;

public class ChoiceFolderDialog extends DialogFragment {
  private final String[] arrayKey;
  private UpdateListInterface UpdateListInterface;

  public ChoiceFolderDialog(String[] noteInfo) {
    this.arrayKey = noteInfo;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    UpdateListInterface = (UpdateListInterface) getContext();
    File folder = new File(requireContext().getFilesDir(), arrayKey[1]);
    FolderUtils FolderUtils = new FolderUtils(folder);
    builder.setItems(
        choiceItems(),
        (dialog, which) -> {
          switch (which) {
            case 0:
              if (FolderUtils.getNotesForFoldersCount() == 0) {
                FolderUtils.deleteFolder();
                UpdateListInterface.RemoveItem(Integer.parseInt(arrayKey[0]));
              } else {
                new DeleteFolderDialog(folder,Integer.parseInt(arrayKey[0])).show(getParentFragmentManager(), "DeleteFolder");
              }
              break;
            case 1:
              new FolderEditAndCreateDialog(arrayKey[1])
                  .show(getParentFragmentManager(), "renameDialog");
              break;
          }
        });

    return builder.create();
  }

  private String[] choiceItems() {
    return  new String[] {getString(R.string.deleteFolder), getString(R.string.renameFolder)};
  }
}
