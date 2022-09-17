package com.pasich.mynotes.ui.view.dialogs.error;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;

public class ErrorTtsDialog extends BottomSheetDialogFragment {


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
        builder.setContentView(R.layout.dialog_message);

        MaterialTextView title = builder.findViewById(R.id.headTextDialog);
        assert title != null;
        title.setText(getString(R.string.error_TTS));

        MaterialTextView message = builder.findViewById(R.id.textMessageDialog);
        assert message != null;
        message.setText(getString(R.string.error_TTS_Message));
        return builder;
    }
}
