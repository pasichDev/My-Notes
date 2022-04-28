package com.pasich.mynotes.Dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;

public class PermissionError extends DialogFragment {
  String mode;

  public PermissionError(String mode) {
    this.mode = mode;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    return builder
        .setTitle(getString(R.string.errorPermisions))
        .setMessage(getString(R.string.errorPermisions_Message))
        .setPositiveButton(
            getString(R.string.open_infoApp),
            (dialog, which) -> {
              Intent intent = new Intent();
              intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
              Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
              intent.setData(uri);
              startActivity(intent);
            })
        .setNegativeButton(getString(R.string.cancel), null)
        .create();
  }
}
