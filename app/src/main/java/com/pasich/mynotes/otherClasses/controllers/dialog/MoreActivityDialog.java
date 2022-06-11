package com.pasich.mynotes.otherClasses.controllers.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.interfaces.MoreActivInterface;
import com.pasich.mynotes.otherClasses.models.ada.ChoiceModel;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;

import java.util.ArrayList;

public class MoreActivityDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ListView listView = new ListView(requireContext());
    final ArrayList<ChoiceModel> arraySortOption = new ArrayList<>();
    final MoreActivInterface MoreActivInterface = (MoreActivInterface) getContext();
    listView.setLayoutAnimation(
        new LayoutAnimationController(
            AnimationUtils.loadAnimation(listView.getContext(), R.anim.item_animation_dialog)));
    listView.setDivider(null);

    arraySortOption.add(
        new ChoiceModel(
            getString(R.string.trashN), R.drawable.ic_trash_menu, "TrashActivity", false));

    arraySortOption.add(
        new ChoiceModel(
            getString(R.string.settings), R.drawable.ic_settings, "SettingsActivity", false));

    DialogListAdapter adapter = new DialogListAdapter(arraySortOption);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(
        (parent, v, position, id) -> {
          if (arraySortOption.get(position).getAction().equals("TrashActivity")) {
            assert MoreActivInterface != null;
            MoreActivInterface.startTrashActivity();
          }
          if (arraySortOption.get(position).getAction().equals("SettingsActivity")) {
            assert MoreActivInterface != null;
            MoreActivInterface.startSettingsActivity();
          }

          dismiss();
        });
    builder.setContentView(listView);
    return builder;
  }
}
