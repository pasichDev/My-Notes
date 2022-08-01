package com.pasich.mynotes.ui.view.dialogs.settings.errorTts.aboutAppDialog;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.ChoiceModel;
import com.pasich.mynotes.ui.view.dialogs.settings.WhatUpdateDialog;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;

import java.util.ArrayList;

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
