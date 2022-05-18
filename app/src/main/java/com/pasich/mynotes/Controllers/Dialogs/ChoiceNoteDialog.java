package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.Model.Adapter.MoreChoiceModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.MoreListAdapter;
import com.pasich.mynotes.Utils.Anim.ListViewAnimation;
import com.pasich.mynotes.View.CustomView.CustomHeadUIDialog;

import java.util.ArrayList;

public class ChoiceNoteDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
    final CustomHeadUIDialog uiDialog = new CustomHeadUIDialog(getContext(), getLayoutInflater());
    final ListView listView = new ListView(getContext());
    final ArrayList<MoreChoiceModel> arrayChoice = new ArrayList<>();

    uiDialog.setHeadTextView(getString(R.string.selectChoice));

    arrayChoice.add(
        new MoreChoiceModel(getString(R.string.selectAll), R.drawable.ic_check_box, "SelectAll"));
    arrayChoice.add(new MoreChoiceModel(getString(R.string.share), R.drawable.ic_share, "Share"));
    arrayChoice.add(new MoreChoiceModel(getString(R.string.tag), R.drawable.ic_tag, "Tag"));
    arrayChoice.add(
        new MoreChoiceModel(getString(R.string.trashNotes), R.drawable.ic_delete, "Delete"));

    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, arrayChoice);
    listView.setAdapter(adapter);
    ListViewAnimation.setListviewAnimationLeftToShow(listView);
    listView.setDivider(null);
    listView.setOnItemClickListener(
        (parent, v, position, id) -> {
          //  if(adapter.getItem(position).getAction().equals("Share"))
          //   shareNotes(getActivity(), );
        });
    // builder.setContentView(listView);

    uiDialog.getContainer().addView(listView);

    uiDialog.getSaveButton().setVisibility(View.GONE);
    uiDialog.getCloseButton().setVisibility(View.GONE);

    builder.setContentView(uiDialog.getContainer());

    return builder;
  }
}
