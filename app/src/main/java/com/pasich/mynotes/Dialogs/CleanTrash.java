package com.pasich.mynotes.Dialogs;

import static com.pasich.mynotes.Сore.Methods.MethodCheckEmptyTrash.checkCountListTrashActivity;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Adapters.ListNotes.DefaultListAdapter;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Сore.File.FileCore;

public class CleanTrash extends DialogFragment {
    private  final DefaultListAdapter defaultListAdapter;

    public CleanTrash(DefaultListAdapter defaultListAdapter)
    {
        this.defaultListAdapter=defaultListAdapter;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FileCore fileCore = new FileCore(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            return builder
                    .setTitle(getString(R.string.trashN))
                    .setMessage(getString(R.string.cleanTrashquestion))
                    .setPositiveButton(getString(R.string.yesCleanTrash), (dialog, which) -> {
                        fileCore.deleteAllNotes();
                        defaultListAdapter.clear();
                        defaultListAdapter.notifyDataSetChanged();
                        checkCountListTrashActivity(getActivity(),defaultListAdapter);
                    })
                    .setNegativeButton(getString(R.string.cancel), null)
                    .create();
    }

}
