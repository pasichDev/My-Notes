package com.pasich.mynotes.ui.view.dialogs.settings.errorTts.aboutAppDialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;

public class ErrorTtsDialog extends BottomSheetDialogFragment {


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
        final ErrorTtsView view = new ErrorTtsView(getLayoutInflater());

        view.setTitle(getString(R.string.error_TTS));

        builder.setContentView(view.getRootContainer());

        return builder;
    }
}
