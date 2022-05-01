package com.pasich.mynotes.Dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.lib.CustomUIDialog;

public class PermissionErrorDialog extends DialogFragment {
  String mode;

  public PermissionErrorDialog(String mode) {
    this.mode = mode;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());
    TextView textMessage = new TextView(getContext());

    uiDialog.setHeadTextView(getString(R.string.errorPermisions));
    textMessage.setText(getString(R.string.errorPermisions_Message));
    uiDialog.setTextSizeMessage(textMessage);
    uiDialog.getContainer().addView(textMessage, uiDialog.lp);
    builder.setView(uiDialog.getContainer());
    return builder
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
