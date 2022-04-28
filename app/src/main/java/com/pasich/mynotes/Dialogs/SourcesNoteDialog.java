package com.pasich.mynotes.Dialogs;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.Adapters.SourceNoteList.SouceListAdapter;
import com.pasich.mynotes.Adapters.SourceNoteList.SourceListContent;
import com.pasich.mynotes.R;
import com.pasich.mynotes.lib.CustomUIDialog;

import java.util.ArrayList;
import java.util.Objects;


public class SourcesNoteDialog extends DialogFragment {

    private final ArrayList<SourceListContent> ListSoc;

    public SourcesNoteDialog(ArrayList<SourceListContent> ListSoc) {
        this.ListSoc = ListSoc;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ListView listView = new ListView(getContext());
        CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());

        uiDialog.setHeadTextView(getString(R.string.investments));
        uiDialog.getContainer().addView(listView);
        uiDialog.getCloseButton().setVisibility(View.VISIBLE);
        uiDialog.getCloseButton().setOnClickListener(view -> Objects.requireNonNull(getDialog()).dismiss());

        builder.setView(uiDialog.getContainer());

        SouceListAdapter souceListAdapter = new SouceListAdapter(getContext(), R.layout.list_source_note, ListSoc);
        listView.setAdapter(souceListAdapter);

        listView.setOnItemClickListener((parent, v, position, id) -> {
            SourceListContent listItem = ListSoc.get(position);
            String selectedItem = listItem.getSource();

            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(selectedItem, selectedItem);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(getContext(), getString(R.string.copyX) + " " + selectedItem, Toast.LENGTH_SHORT).show();
            Objects.requireNonNull(getDialog()).dismiss();
        });

        return builder.create();
    }

}
