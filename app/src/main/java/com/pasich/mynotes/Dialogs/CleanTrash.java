package com.pasich.mynotes.Dialogs;

import static com.pasich.mynotes.Сore.Methods.MethodCheckEmptyTrash.checkCountListTrashActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.R;
import com.pasich.mynotes.lib.CustomUIDialog;
import com.pasich.mynotes.Сore.File.FileCore;

public class CleanTrash extends DialogFragment {
  private final DefaultListAdapter defaultListAdapter;

  public CleanTrash(DefaultListAdapter defaultListAdapter) {
    this.defaultListAdapter = defaultListAdapter;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    FileCore fileCore = new FileCore(getContext());
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());
    uiDialog.setHeadTextView(getString(R.string.trashN));

    TextView textMessage = new TextView(getContext());
    textMessage.setText(getString(R.string.cleanTrashquestion));

    uiDialog.getContainer().addView(textMessage, uiDialog.lp);
    builder.setView(uiDialog.getContainer());

    builder
        .setPositiveButton(
            getString(R.string.yesCleanTrash),
            (dialog, which) -> {
              fileCore.deleteAllNotes();
              defaultListAdapter.clear();
              defaultListAdapter.notifyDataSetChanged();
              if (defaultListAdapter.getCount() == 0) checkCountListTrashActivity(getActivity());
            })
        .setNegativeButton(getString(R.string.cancel), null);
    return builder.create();
  }
}
