package com.pasich.mynotes.controllers.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.MoreListAdapter;
import com.pasich.mynotes.Utils.Interface.ManageTrash;
import com.pasich.mynotes.View.DialogView.CleanTrashView;
import com.pasich.mynotes.models.adapter.ChoiceModel;

import java.util.ArrayList;

public class CleanTrashDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ManageTrash ManageTrash = (ManageTrash) getContext();
    final CleanTrashView CleanTrashView = new CleanTrashView(requireContext(), getLayoutInflater());

    ArrayList<ChoiceModel> arrayChoice = new ArrayList<>();
    arrayChoice.add(
        new ChoiceModel(getString(R.string.yesCleanTrash), R.drawable.ic_delete, "Delete", false));

    arrayChoice.add(
        new ChoiceModel(
            getString(R.string.cancel), R.drawable.ic_close_search_view, "Close", false));
    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, arrayChoice);
    CleanTrashView.listView.setAdapter(adapter);

    CleanTrashView.listView.setOnItemClickListener(
        (parent, v, position, id) -> {
          String action = adapter.getItem(position).getAction();
          if (!action.equals("Close")) {
            assert ManageTrash != null;
            ManageTrash.cleanTrash();
          }
          dismiss();
        });
    builder.setContentView(CleanTrashView.getContainer());
    return builder;
  }
}
