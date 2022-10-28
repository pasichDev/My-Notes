package com.pasich.mynotes.ui.view.dialogs.trash;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.trash.source.TrashRepository;

public class CleanTrashDialog extends BottomSheetDialogFragment {

    private final TrashRepository repository; //INJECT

    public CleanTrashDialog(TrashRepository repository) {
        this.repository = repository;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        builder.setContentView(R.layout.dialog_clean_trash);

        MaterialTextView title = builder.findViewById(R.id.headTextDialog);
        title.setText(R.string.trashClean);

        MaterialTextView message = builder.findViewById(R.id.textMessageDialog);
        message.setText(R.string.cleanTrashMessage);

        builder.findViewById(R.id.yesCleanTrash).setOnClickListener(v -> {
            repository.deleteAll();
            dismiss();
        });
        builder.findViewById(R.id.cancel).setOnClickListener(v -> dismiss());

        return builder;
    }
}
