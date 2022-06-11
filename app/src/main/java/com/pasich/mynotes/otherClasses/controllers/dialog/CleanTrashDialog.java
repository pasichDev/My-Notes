package com.pasich.mynotes.otherClasses.controllers.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.interfaces.ManageTrash;
import com.pasich.mynotes.otherClasses.models.ada.ChoiceModel;
import com.pasich.mynotes.otherClasses.view.dialog.CleanTrashView;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;

import java.util.ArrayList;

public class CleanTrashDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ManageTrash ManageTrash = (ManageTrash) getContext();
    final CleanTrashView view = new CleanTrashView(getLayoutInflater());

    ArrayList<ChoiceModel> arrayChoice = new ArrayList<>();
    arrayChoice.add(
        new ChoiceModel(getString(R.string.yesCleanTrash), R.drawable.ic_delete, "Delete", false));

    arrayChoice.add(
        new ChoiceModel(
            getString(R.string.cancel), R.drawable.ic_close_search_view, "Close", false));
    DialogListAdapter adapter = new DialogListAdapter(arrayChoice);
    view.getItemsView().setAdapter(adapter);

    view.getItemsView()
        .setOnItemClickListener(
            (parent, v, position, id) -> {
              String action = adapter.getItem(position).getAction();
              if (!action.equals("Close")) {
                assert ManageTrash != null;
                ManageTrash.cleanTrash();
              }
              dismiss();
            });
    builder.setContentView(view.getRootContainer());
    return builder;
  }
}
