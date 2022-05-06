package com.pasich.mynotes.Dialogs;

import static com.pasich.mynotes.Utils.Utils.ShareNotesMethodUtils.shareNotes;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.Adapters.ListNotes.ListNotesModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.FileCore;

import java.io.File;
import java.util.ArrayList;

public class ChoiceListDialog extends DialogFragment {
  private final ArrayList listNotesfors;
  private final int pos;
  private final String folderOutput;
  private final boolean folder;
  private final DefaultListAdapter defaultListAdapter;
  private FileCore fileCore;

  public ChoiceListDialog(
      int pos,
      ArrayList ListNotesfors,
      DefaultListAdapter defaultListAdapter,
      boolean folder,
      String folderOutput) {
    this.pos = pos;
    this.defaultListAdapter = defaultListAdapter;
    this.listNotesfors = ListNotesfors;
    this.folder = folder;
    this.folderOutput = folderOutput;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    fileCore = new FileCore(getContext());
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    String[] choise = choiseItems();
    ListNotesModel listNotesfor = (ListNotesModel) listNotesfors.get(pos);
    String selectedItem = listNotesfor.getNameList();

    builder.setItems(
        choise,
        (dialog, which) -> {
          switch (which) {
            case 0:
              // Удалить заметку или удалить папку
              if (folder == false) {
                fileCore.transferNotes(selectedItem, "trash", folderOutput);
                updateListView(pos);
              } else {
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
                }
              }

              break;
            case 1: // Поделиться или переименовать папку
              if (folder == false) {
                shareNotes(getActivity(), fileCore.readFile(selectedItem, "").toString());
              } else {

                FragmentManager fm = getParentFragmentManager();
                FolderOptionDialog dialogFragment = new FolderOptionDialog(selectedItem);
                dialogFragment.setTargetFragment(getTargetFragment(), 300);
                dialogFragment.show(fm, "renameFolder");
              }
              break;
            case 2: // Перместить заметку
              CopyNotesDialog copyNotes =
                  new CopyNotesDialog(listNotesfors, defaultListAdapter, selectedItem, pos, folderOutput);
              assert getFragmentManager() != null;
              copyNotes.show(getFragmentManager(), "Copy Notes");

              break;
          }
        });

    AlertDialog dialog = builder.create();
    dialog.show();
    return dialog;
  }

  private void updateListView(int position) {
    listNotesfors.remove(position);
    defaultListAdapter.notifyDataSetChanged();
  }

  private String[] choiseItems() {
    String[] returnCH;
    if (folder == false) {
      if (!(fileCore.getCountFolder() == 0)) {
        returnCH =
            new String[] {
              getString(R.string.trashNotes),
              getString(R.string.share),
              getString(R.string.copyNotes)
            };
      } else {
        returnCH = new String[] {getString(R.string.trashNotes), getString(R.string.share)};
      }
    } else {
      returnCH = new String[] {getString(R.string.deleteFolder), getString(R.string.renameFolder)};
    }
    return returnCH;
  }
}
