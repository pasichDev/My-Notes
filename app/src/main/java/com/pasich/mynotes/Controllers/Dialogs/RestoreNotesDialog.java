package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomHeadUIDialog;

public class RestoreNotesDialog extends DialogFragment {

  public interface continueImport {
    void continueImportMethod();
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    continueImport listen = (continueImport) getTargetFragment();
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    CustomHeadUIDialog uiDialog = new CustomHeadUIDialog(getContext(), getLayoutInflater());
    uiDialog.setHeadTextView(getString(R.string.warning));

    TextView textMessage = new TextView(getContext());
    textMessage.setText(getString(R.string.restoreDialogMessage));
    uiDialog.setTextSizeMessage(textMessage);
    uiDialog.getContainer().addView(textMessage, uiDialog.LP_DEFAULT);
    builder.setView(uiDialog.getContainer());

    return builder
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
