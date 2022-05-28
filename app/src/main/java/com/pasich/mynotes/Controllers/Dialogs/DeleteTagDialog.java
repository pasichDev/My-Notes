package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.Models.Adapter.MoreChoiceModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.MoreListAdapter;
import com.pasich.mynotes.Utils.Interface.ManageTag;
import com.pasich.mynotes.View.DialogView.DeleteTagView;

import java.util.ArrayList;

public class DeleteTagDialog extends DialogFragment {

  private final int countNotesToTag;

  public DeleteTagDialog(int countNotesToTag) {
    this.countNotesToTag = countNotesToTag;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ManageTag ManageTag = (ManageTag) getContext();
    final DeleteTagView DeleteTagView = new DeleteTagView(requireContext(), getLayoutInflater());

    ArrayList<MoreChoiceModel> arrayChoice = new ArrayList<>();
    arrayChoice.add(
        new MoreChoiceModel(getString(R.string.deleteTag), R.drawable.ic_delete, "Delete", false));
    if (countNotesToTag != 0)
      arrayChoice.add(
          new MoreChoiceModel(
              getString(R.string.deleteTagAndNotes),
              R.drawable.ic_delete_notes_tag,
              "DeleteAndNotes",
              false));
    arrayChoice.add(
        new MoreChoiceModel(
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
            ManageTag.deleteTag(action.equals("DeleteAndNotes"));
          }
          dismiss();
        });
    builder.setContentView(DeleteTagView.getContainer());
    return builder;
  }
}
