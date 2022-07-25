package com.pasich.mynotes.ui.view.dialogs.trash.CleanTrashDialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.ChoiceModel;
import com.pasich.mynotes.base.view.TrashView;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;

import java.util.ArrayList;

public class CleanTrashDialog extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        final TrashView trashView = (TrashView) requireContext();
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
                                trashView.cleanTrashYes();
                            }
                            dismiss();
                        });
        builder.setContentView(view.getRootContainer());
        return builder;
    }
}
