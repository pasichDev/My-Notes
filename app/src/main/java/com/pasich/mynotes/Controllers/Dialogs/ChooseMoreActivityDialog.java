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
import com.pasich.mynotes.Utils.Interface.MoreActivInterface;

import java.util.ArrayList;

public class ChooseMoreActivityDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ListView listView = new ListView(requireContext());
    final ArrayList<MoreChoiceModel> arraySortOption = new ArrayList<>();
    final MoreActivInterface MoreActivInterface = (MoreActivInterface) getContext();
    ListViewAnimation.setListviewAnimationLeftToShow(listView);
    listView.setDivider(null);
    arraySortOption.add(
        new MoreChoiceModel(
            getString(R.string.trashN), R.drawable.ic_trash_menu, "TrashActivity", false));

    arraySortOption.add(
        new MoreChoiceModel(
            getString(R.string.settings), R.drawable.ic_settings, "SettingsActivity", false));

    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, arraySortOption);
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
