package com.pasich.mynotes.Controllers.Dialogs;


import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;
import com.pasich.mynotes.View.CustomView.CustomUIDialog;
import com.pasich.mynotes.Utils.File.FileCore;

public class CleanTrashDialog extends DialogFragment {


  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    FileCore fileCore = new FileCore(getContext());
      UpdateListInterface UpdateListInterface = (UpdateListInterface) getContext();
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

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
              fileCore.deleteAllNotes();
              assert UpdateListInterface != null;
              UpdateListInterface.RestartListView();
            })
        .setNegativeButton(getString(R.string.cancel), null);
    return builder.create();
  }
}
