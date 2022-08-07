package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.model.ChoiceModel;
import com.pasich.mynotes.ui.view.activity.SettingsActivity;
import com.pasich.mynotes.ui.view.activity.TrashActivity;
import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;

import java.util.ArrayList;

public class MoreActivityDialog extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        final ListDialogView listView = new ListDialogView(getLayoutInflater());
        final ArrayList<ChoiceModel> arraySortOption = new ArrayList<>();

        arraySortOption.add(
                new ChoiceModel(
                        getString(R.string.trashN), R.drawable.ic_trash_menu, "TrashActivity", false));

        arraySortOption.add(
                new ChoiceModel(
                        getString(R.string.settings), R.drawable.ic_settings, "SettingsActivity", false));

        DialogListAdapter adapter = new DialogListAdapter(arraySortOption);
        listView.addView(listView.getItemsView());
        listView.getItemsView().setAdapter(adapter);
        listView.getItemsView().setOnItemClickListener(
                (parent, v, position, id) -> {
                    if (arraySortOption.get(position).getAction().equals("TrashActivity")) {
                        startActivity(new Intent(getActivity(), TrashActivity.class));
                    }
                    if (arraySortOption.get(position).getAction().equals("SettingsActivity")) {
                        startActivity(new Intent(getActivity(), SettingsActivity.class));
                    }

                    dismiss();
                });
        builder.setContentView(listView.getRootContainer());
        return builder;
    }
}
