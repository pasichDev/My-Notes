package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.File.FileCore;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;
import com.pasich.mynotes.View.CustomView.CustomUIDialog;
import com.pasich.mynotes.View.FolderEditAndCreateView;

public class FolderEditAndCreateDialog extends DialogFragment {

  private final String editName;
  private FolderEditAndCreateView DialogView;
  private FileCore fileCore;
  private UpdateListInterface RestartListInterface;

  public FolderEditAndCreateDialog(String editName) {
    this.editName = editName;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    fileCore = new FileCore(getContext());
    DialogView = new FolderEditAndCreateView(getContext());
    RestartListInterface = (UpdateListInterface) getContext();
    CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());

    uiDialog.setHeadTextView(
        getString(editName.equals("") ? R.string.newFolder : R.string.renameFolder));
    uiDialog.getContainer().addView(DialogView.input, uiDialog.lp);

    if (editName.length() >= 1) DialogView.input.setText(editName);
    DialogView.setToTextInput();

    builder.setView(uiDialog.getContainer());

    builder.setPositiveButton(
        editName.trim().length() >= 1 ? getString(R.string.save) : getString(R.string.createFolder),
        (dialog, which) -> {
          if (editName.trim().length() >= 1) {
            if (!DialogView.getTextInput().equals(editName)) saveFolder();
          } else if (editName.trim().length() == 0) {
            if (DialogView.getTextInput().length() >= 1) createFolder();
          }
        });

    ((InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE))
        .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);

    return builder.create();
  }

  private void saveFolder() {
    fileCore.saveNameFolder(DialogView.input.getText().toString(), true, editName);
    assert RestartListInterface != null;
    RestartListInterface.RestartListView();
  }

  private void createFolder() {
    fileCore.saveNameFolder(DialogView.input.getText().toString(), false, "");
    assert RestartListInterface != null;
    RestartListInterface.RestartListView();
  }

  @Override
  public void onDismiss(@NonNull DialogInterface dialog) {
    super.onDismiss(dialog);
    requireActivity()
        .getWindow()
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
  }
}
