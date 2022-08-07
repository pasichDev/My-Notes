package com.pasich.mynotes.ui.view.dialogs.settings.errorTts;

import android.app.Dialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;

public class ErrorTtsDialog extends BottomSheetDialogFragment {


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
        final ListDialogView view = new ListDialogView(getLayoutInflater());

        view.addTitle(getString(R.string.error_TTS));

        TextView textMessage = new TextView(getContext());
        textMessage.setText(getString(R.string.error_TTS_Message));
        textMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, view.TEXT_MESSAGE_SIZE);
        view.addView(textMessage, view.LP_DEFAULT);
        builder.setContentView(view.getRootContainer());

        return builder;
    }
}
