package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.FolderUtils;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;
import com.pasich.mynotes.View.CustomView.CustomUIDialog;

import java.io.File;
import java.util.Objects;

public class DeleteFolderDialog extends DialogFragment {

  private UpdateListInterface UpdateListInterface;
  private File folder;
  private int position;

  public DeleteFolderDialog(File folder, int position){
    this.folder = folder;
    this.position = position;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    UpdateListInterface = (UpdateListInterface) getContext();
    FolderUtils FolderUtils = new FolderUtils(folder);
    CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());
    uiDialog.setHeadTextView(getString(R.string.warning));
    TextView textMessage = new TextView(getContext());

    textMessage.setText(getString(R.string.deleteFolderIsFiles));
    uiDialog.setTextSizeMessage(textMessage);
    uiDialog.getContainer().addView(textMessage, uiDialog.lp);

    uiDialog.getCloseButton().setVisibility(View.VISIBLE);
    uiDialog
        .getCloseButton()
        .setOnClickListener(view -> Objects.requireNonNull(getDialog()).dismiss());
    builder.setView(uiDialog.getContainer());

    return builder
        .setPositiveButton(
            getString(R.string.yesDeleteIsFiles),
            (dialog, which) -> {

              FolderUtils.deleteFolderForce();
              UpdateListInterface.RemoveItem(position);
            })
        .create();
  }
}
