package com.pasich.mynotes.controllers.dialog;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.models.adapter.SourceListModel;
import com.pasich.mynotes.utils.Adapters.SourceListAdapter;
import com.pasich.mynotes.view.customView.TitleDialog;

import java.util.ArrayList;
import java.util.Objects;

public class SourcesNoteDialog extends DialogFragment {

  private final ArrayList<SourceListModel> ListSoc;

  public SourcesNoteDialog(ArrayList<SourceListModel> ListSoc) {
    this.ListSoc = ListSoc;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    ListView listView = new ListView(getContext());
    TitleDialog uiDialog = new TitleDialog(getContext(), getLayoutInflater());

    uiDialog.setHeadTextView(getString(R.string.investments));
    uiDialog.getContainer().addView(listView);

    builder.setView(uiDialog.getContainer());

    SourceListAdapter souceListAdapter =
        new SourceListAdapter(getContext(), R.layout.item_icon_text_simple, ListSoc);
    listView.setAdapter(souceListAdapter);

    listView.setOnItemClickListener(
        (parent, v, position, id) -> {
          SourceListModel listItem = ListSoc.get(position);
          String selectedItem = listItem.getSource();

          ClipboardManager clipboard =
              (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
          ClipData clip = ClipData.newPlainText(selectedItem, selectedItem);
          clipboard.setPrimaryClip(clip);

          Toast.makeText(
                  getContext(), getString(R.string.copyX) + " " + selectedItem, Toast.LENGTH_SHORT)
              .show();
          Objects.requireNonNull(getDialog()).dismiss();
        });

    return builder.create();
  }
}
