package com.pasich.mynotes.ui.view.dialogs.DeleteTagDialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.ChoiceModel;
import com.pasich.mynotes.base.interfaces.ManageTag;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;

import java.util.ArrayList;

public class DeleteTagDialog extends DialogFragment {

  private final int countNotesToTag;
  private final int positionTag;

  public DeleteTagDialog(int countNotesToTag, int position) {
    this.countNotesToTag = countNotesToTag;
    this.positionTag = position;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ManageTag ManageTag = (ManageTag) getContext();
    final DeleteTagView view = new DeleteTagView(getLayoutInflater());
    ArrayList<ChoiceModel> arrayChoice = new ArrayList<>();
    arrayChoice.add(
        new ChoiceModel(getString(R.string.deleteTag), R.drawable.ic_delete, "Delete", false));
    if (countNotesToTag != 0)
      arrayChoice.add(
          new ChoiceModel(
              getString(R.string.deleteTagAndNotes),
              R.drawable.ic_delete,
              "DeleteAndNotes",
              false));
    arrayChoice.add(
        new ChoiceModel(
            getString(R.string.cancel), R.drawable.ic_close_search_view, "Close", false));
    DialogListAdapter adapter = new DialogListAdapter(arrayChoice);
    view.getItemsView().setAdapter(adapter);

    view.getItemsView().setDivider(null);
    view.getItemsView()
        .setOnItemClickListener(
            (parent, v, position, id) -> {
              String action = adapter.getItem(position).getAction();
              if (!action.equals("Close")) {
                assert ManageTag != null;
                ManageTag.deleteTag(action.equals("DeleteAndNotes"), positionTag);
              }
              dismiss();
            });
    builder.setContentView(view.getRootContainer());
    return builder;
  }
}
