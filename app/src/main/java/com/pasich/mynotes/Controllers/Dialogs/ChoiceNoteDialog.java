package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.Models.Adapter.MoreChoiceModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.MoreListAdapter;
import com.pasich.mynotes.Utils.Anim.ListViewAnimation;
import com.pasich.mynotes.Utils.Interface.ChoiceNoteInterface;

import java.util.ArrayList;

public class ChoiceNoteDialog extends DialogFragment {

  private final int Item;
  private final int noteID;

  public ChoiceNoteDialog(int item, int noteID) {
    this.Item = item;
    this.noteID = noteID;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
    final ListView listView = new ListView(getContext());
    final ArrayList<MoreChoiceModel> arrayChoice = new ArrayList<>();
    final ChoiceNoteInterface ChoiceNoteInterface = (ChoiceNoteInterface) getContext();

    arrayChoice.add(
        new MoreChoiceModel(
            getString(R.string.selectAll), R.drawable.ic_check_box, "SelectAll", false));
    arrayChoice.add(
        new MoreChoiceModel(getString(R.string.share), R.drawable.ic_share, "Share", false));
    arrayChoice.add(new MoreChoiceModel(getString(R.string.tag), R.drawable.ic_tag, "Tag", false));
    arrayChoice.add(
        new MoreChoiceModel(getString(R.string.trashNotes), R.drawable.ic_delete, "Delete", false));

    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, arrayChoice);
    listView.setAdapter(adapter);
    ListViewAnimation.setListviewAnimationLeftToShow(listView);
    listView.setDivider(null);
    listView.setOnItemClickListener(
        (parent, v, position, id) -> {
          if (adapter.getItem(position).getAction().equals("Share")) {
            assert ChoiceNoteInterface != null;
            ChoiceNoteInterface.shareNote(Item);
          }
          if (adapter.getItem(position).getAction().equals("Delete")) {
            assert ChoiceNoteInterface != null;
            ChoiceNoteInterface.deleteNote(noteID, Item);
          }
          if (adapter.getItem(position).getAction().equals("Tag")) {
            new ChooseTagDialog(noteID, Item).show(getParentFragmentManager(), "EditDIalog");
          }
          if (adapter.getItem(position).getAction().equals("SelectAll")) {
            assert ChoiceNoteInterface != null;
            ChoiceNoteInterface.actionNote(Item);
          }
          dismiss();
        });

    builder.setContentView(listView);

    return builder;
  }
}
