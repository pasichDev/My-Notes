package com.pasich.mynotes.Dialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;

public class RestoreNotes extends DialogFragment {

  public interface continueImport {
    void continueImportMethod();
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    continueImport listen = (continueImport) getTargetFragment();
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    return builder
        .setTitle(getString(R.string.warning))
        .setMessage(getString(R.string.restoreDialogMessage))
        .setPositiveButton(
            getString(R.string.continueNext),
            (dialog, which) -> {
              assert listen != null;
              listen.continueImportMethod();
            })
        .setNegativeButton(getString(R.string.cancel), null)
        .create();
  }
}
