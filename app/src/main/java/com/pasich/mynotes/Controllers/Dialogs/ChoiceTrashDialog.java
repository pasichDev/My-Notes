package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Controllers.Activity.NoteActivity;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.CopyFileUtils;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;

import java.io.File;

public class ChoiceTrashDialog extends DialogFragment {
  private final String[] arrayNoteInfo;

  public ChoiceTrashDialog(String[] array) {
    this.arrayNoteInfo = array;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    UpdateListInterface UpdateListInterface = (UpdateListInterface) getContext();
    CopyFileUtils CopyFileUtils =
        new CopyFileUtils(
            new File(requireContext().getFilesDir() + "/trash", arrayNoteInfo[1]),
            new File(requireContext().getFilesDir(), ""));

    builder.setItems(
        new String[] {getString(R.string.OpenViewNotes), getString(R.string.removeNotes)},
        (dialog, which) -> {
          switch (which) {
            case 0:
              Intent intent = new Intent(getActivity(), NoteActivity.class);
              intent.putExtra("KeyFunction", "TrashNote");
              intent.putExtra("idNote", arrayNoteInfo[1]);
              startActivityForResult(intent, 1);
              break;
            case 1:
              CopyFileUtils.moveFile();
              assert UpdateListInterface != null;
              UpdateListInterface.RemoveItem(Integer.parseInt(arrayNoteInfo[0]));
              break;
          }
        });

    return builder.create();
  }
}
