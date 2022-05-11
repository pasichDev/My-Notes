package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Adapters.SpinnerNotes.FolderSpinnerAdapter;
import com.pasich.mynotes.Model.DialogModel.CopyNotesModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.CopyFileUtils;
import com.pasich.mynotes.Utils.File.FileCore;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;
import com.pasich.mynotes.View.DialogView.CopyNotesView;

import java.io.File;
import java.util.List;

public class CopyNotesDialog extends DialogFragment {
  private final String[] arrayNoteInfo;
  UpdateListInterface UpdateListInterface;
  private int getItem;

  public CopyNotesDialog(String[] arrayKey) {
    this.arrayNoteInfo = arrayKey;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    FileCore fileCore = new FileCore(getContext());
    CopyNotesView CopyNotesView = new CopyNotesView(requireContext(), getLayoutInflater());
    CopyFileUtils CopyFileUtils = new CopyFileUtils( new File(requireContext().getFilesDir() + "/" + arrayNoteInfo[2], arrayNoteInfo[1]),
              new File(requireContext().getFilesDir() + "/trash", ""));


    List<String> folderListArray =
        new CopyNotesModel(requireContext().getFilesDir(), arrayNoteInfo[2]).folderListArray;
    UpdateListInterface = (UpdateListInterface) requireContext();
    CopyNotesView.spinner.setAdapter(new FolderSpinnerAdapter(getContext(), folderListArray));

    builder.setView(CopyNotesView.uiDialog.getContainer());
    CopyNotesView.spinner.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(
              AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
            getItem = selectedItemPosition;
          }

          public void onNothingSelected(AdapterView<?> parent) {}
        });

    builder.setPositiveButton(
        getString(R.string.save),
        (dialog, which) -> {
            CopyFileUtils.copyFile();
          fileCore.transferNotes(arrayNoteInfo[1], folderListArray.get(getItem), arrayNoteInfo[2]);

          UpdateListInterface.RemoveItem(Integer.parseInt(arrayNoteInfo[0]));
        });

    return builder.create();
  }
}
