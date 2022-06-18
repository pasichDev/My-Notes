package com.pasich.mynotes.ui.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.otherClasses.models.ada.ChoiceModel;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;

import java.util.ArrayList;

public class MoreNoteDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    ListView listView = new ListView(getContext());

    ArrayList<ChoiceModel> arrayChoice = new ArrayList<>();
    arrayChoice.add(
        new ChoiceModel(getString(R.string.share), R.drawable.ic_share, "Share", false));
    arrayChoice.add(new ChoiceModel(getString(R.string.tag), R.drawable.ic_tag, "Tag", false));
    arrayChoice.add(
        new ChoiceModel(getString(R.string.trashNotes), R.drawable.ic_delete, "Delete", false));
    arrayChoice.add(
        new ChoiceModel(
            getString(R.string.noSave), R.drawable.ic_close_search_view, "Close", false));
    DialogListAdapter adapter = new DialogListAdapter(arrayChoice);
    listView.setAdapter(adapter);
    listView.setLayoutAnimation(
        new LayoutAnimationController(
            AnimationUtils.loadAnimation(listView.getContext(), R.anim.item_animation_dialog)));

    listView.setDivider(null);
    listView.setOnItemClickListener(
        (parent, v, position, id) -> {

        });
    builder.setContentView(listView);
    return builder;
  }
}
