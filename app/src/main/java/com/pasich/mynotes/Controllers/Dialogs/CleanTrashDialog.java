package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.FolderUtils;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;
import com.pasich.mynotes.View.CustomView.CustomUIDialog;

import java.io.File;

public class CleanTrashDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    FolderUtils FolderUtils = new FolderUtils(new File(requireContext().getFilesDir(), "trash"));
    UpdateListInterface UpdateListInterface = (UpdateListInterface) getContext();
    CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());
    uiDialog.setHeadTextView(getString(R.string.trashN));

    TextView textMessage = new TextView(getContext());
    textMessage.setText(getString(R.string.cleanTrashquestion));
    uiDialog.setTextSizeMessage(textMessage);
    uiDialog.getContainer().addView(textMessage, uiDialog.lp);
    builder.setView(uiDialog.getContainer());

    builder
        .setPositiveButton(
            getString(R.string.yesCleanTrash),
            (dialog, which) -> {
              FolderUtils.cleanFolder();
              assert UpdateListInterface != null;
              UpdateListInterface.RestartListView();
            })
        .setNegativeButton(getString(R.string.cancel), null);
    return builder.create();
  }
}
