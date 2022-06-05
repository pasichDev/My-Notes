package com.pasich.mynotes.controllers.dialog.tags;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.MoreListAdapter;
import com.pasich.mynotes.Utils.Interface.ManageTag;
import com.pasich.mynotes.View.DialogView.DeleteTagView;
import com.pasich.mynotes.models.adapter.ChoiceModel;

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
    final DeleteTagView DeleteTagView = new DeleteTagView(requireContext(), getLayoutInflater());

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
    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, arrayChoice);
    DeleteTagView.listView.setAdapter(adapter);

    DeleteTagView.listView.setDivider(null);
    DeleteTagView.listView.setOnItemClickListener(
        (parent, v, position, id) -> {
          String action = adapter.getItem(position).getAction();
          if (!action.equals("Close")) {
            assert ManageTag != null;
            ManageTag.deleteTag(action.equals("DeleteAndNotes"), positionTag);
          }
          dismiss();
        });
    builder.setContentView(DeleteTagView.getContainer());
    return builder;
  }
}
