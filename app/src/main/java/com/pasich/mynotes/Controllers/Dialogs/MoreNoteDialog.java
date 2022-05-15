package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.Model.Adapter.MoreChoiceModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.MoreListAdapter;

import java.util.ArrayList;

public class MoreNoteDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    ListView listView = new ListView(getContext());

    ArrayList<MoreChoiceModel> arrayChoice = new ArrayList<>();
    arrayChoice.add(new MoreChoiceModel(getString(R.string.share), R.drawable.ic_share));
    arrayChoice.add(new MoreChoiceModel(getString(R.string.cd_deleteNote), R.drawable.ic_delete));
    arrayChoice.add(new MoreChoiceModel(getString(R.string.noSave), R.drawable.ic_close));

    // {getString(R.string.share),getString(R.string.cd_deleteNote),getString(R.string.noSave)};

    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, arrayChoice);
    listView.setAdapter(adapter);

    builder.setContentView(listView);
    return builder;
  }
}
