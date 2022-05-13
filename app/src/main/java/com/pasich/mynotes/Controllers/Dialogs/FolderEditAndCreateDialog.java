package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Check.CheckNamesFoldersUtils;
import com.pasich.mynotes.Utils.File.FolderSaveAndCreateUtils;
import com.pasich.mynotes.Utils.Interface.UpdateListInterface;
import com.pasich.mynotes.View.CustomView.CustomUIDialog;
import com.pasich.mynotes.View.DialogView.FolderEditAndCreateView;

public class FolderEditAndCreateDialog extends DialogFragment {

  private final String editName;
  private FolderEditAndCreateView DialogView;
  private UpdateListInterface RestartListInterface;
  private FolderSaveAndCreateUtils FolderSaveAndCreateUtils;

  public FolderEditAndCreateDialog(String editName) {
    this.editName = editName;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
    DialogView = new FolderEditAndCreateView(getContext());
    RestartListInterface = (UpdateListInterface) getContext();
    FolderSaveAndCreateUtils =
        new FolderSaveAndCreateUtils(requireContext().getFilesDir().getPath());
    CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());

    uiDialog.setHeadTextView(
        getString(editName.equals("") ? R.string.newFolder : R.string.renameFolder));
    uiDialog.getContainer().addView(DialogView.input, uiDialog.lp);

    if (editName.length() >= 1) DialogView.input.setText(editName);
    DialogView.setToTextInput();

    builder.setView(uiDialog.getContainer());

    builder.setPositiveButton(
        editName.trim().length() >= 1 ? getString(R.string.save) : getString(R.string.createFolder),
        (dialog, which) -> initialize(DialogView.input.getText().toString()));

    ((InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE))
        .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);

    return builder.create();
  }

  /**
   * Method that selects a folder name and implements the selected action
   *
   * @param nameFolder - folder name (String)
   */
  private void initialize(String nameFolder) {
    if (new CheckNamesFoldersUtils().getMatchFolders(nameFolder)) {
      Toast.makeText(getContext(), getString(R.string.error_folder_system), Toast.LENGTH_SHORT)
          .show();
    } else if (nameFolder.trim().length() == 0) {
      Toast.makeText(getContext(), getString(R.string.error_name_folder), Toast.LENGTH_SHORT)
          .show();
    } else {
      if (editName.trim().length() >= 1) {
        if (!DialogView.getTextInput().equals(editName))
          FolderSaveAndCreateUtils.renameFolder(nameFolder, editName);
      } else if (editName.trim().length() == 0) {
        if (DialogView.getTextInput().length() >= 1)
          FolderSaveAndCreateUtils.createdFolder(nameFolder);
      }
      assert RestartListInterface != null;
      RestartListInterface.RestartListView();
    }
  }

  @Override
  public void onDismiss(@NonNull DialogInterface dialog) {
    super.onDismiss(dialog);
    requireActivity()
        .getWindow()
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
  }
}
