package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.activity.SettingsActivity;
import com.pasich.mynotes.ui.view.activity.TrashActivity;

public class OtherActivityDialog extends BottomSheetDialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        builder.setContentView(R.layout.dialog_other_activity);

        builder.findViewById(R.id.settingsActivity).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            dismiss();
        });
        builder.findViewById(R.id.trashActivityLayout).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), TrashActivity.class));
            dismiss();
        });

        return builder;
    }
}
