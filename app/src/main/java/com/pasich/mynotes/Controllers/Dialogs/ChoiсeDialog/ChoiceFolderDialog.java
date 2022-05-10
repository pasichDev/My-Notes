package com.pasich.mynotes.Controllers.Dialogs.ChoiсeDialog;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Controllers.Dialogs.FolderEditAndCreateDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.FolderUtils;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;

import java.io.File;
import java.io.IOException;

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
    FolderUtils FolderUtils = new FolderUtils(new File(requireContext().getFilesDir(), arrayKey[1]));
    builder.setItems(
        choiceItems(),
        (dialog, which) -> {
          switch (which) {
            case 0:
             // if(FolderUtils.getNotesForFoldersCount() >= 1){
                  FolderUtils.deleteFolder();
                  UpdateListInterface.RemoveItem(Integer.parseInt(arrayKey[0]));


           //   }

              /*
              File folderSelected =
                      new File(getContext().getFilesDir() + "/" + selectedItem + "/");
              int columFiles = folderSelected.list().length;
              if (columFiles == 0) {
                fileCore.deleteFolder(selectedItem);
                updateListView(pos);
              } else if (columFiles >= 1) {
                AlertDialog.Builder dilogDeleteFolders = new AlertDialog.Builder(getActivity());
                dilogDeleteFolders.setTitle(getString(R.string.warning));
                dilogDeleteFolders.setMessage(getString(R.string.deleteFolderIsFiles));
                dilogDeleteFolders.setPositiveButton(
                        getString(R.string.yesDeleteIsFiles),
                        (dialog1, which1) -> {
                          fileCore.deleteFolder(selectedItem);
                          updateListView(pos);
                        });
                dilogDeleteFolders.setNegativeButton(getString(R.string.cancel), null);
                dilogDeleteFolders.show();
              }*/
              break;
            case 1:
            new FolderEditAndCreateDialog(arrayKey[1]).show(getParentFragmentManager(), "renameDialog");
              break;

          }
        });

    return builder.create();
  }

  private String[] choiceItems() {
    return  new String[] {getString(R.string.deleteFolder), getString(R.string.renameFolder)};
  }
}
