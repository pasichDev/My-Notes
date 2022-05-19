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
import com.pasich.mynotes.Utils.Anim.ListViewAnimation;

import java.util.ArrayList;

public class MoreNoteDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    ListView listView = new ListView(getContext());

    ArrayList<MoreChoiceModel> arrayChoice = new ArrayList<>();
    arrayChoice.add(new MoreChoiceModel(getString(R.string.share), R.drawable.ic_share, "Share"));
    arrayChoice.add(new MoreChoiceModel(getString(R.string.tag), R.drawable.ic_tag, "Tag"));
    arrayChoice.add(
        new MoreChoiceModel(getString(R.string.trashNotes), R.drawable.ic_delete, "Delete"));
    arrayChoice.add(new MoreChoiceModel(getString(R.string.noSave), R.drawable.ic_close, "Close"));
    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, arrayChoice);
    listView.setAdapter(adapter);
    ListViewAnimation.setListviewAnimationLeftToShow(listView);
    listView.setDivider(null);
    listView.setOnItemClickListener(
        (parent, v, position, id) -> {

        });
    builder.setContentView(listView);
    return builder;
  }
}
