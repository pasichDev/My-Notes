package com.pasich.mynotes.ui.view.dialogs.settings;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.utils.ShareUtils;

public class AboutAppDialog extends BottomSheetDialogFragment {


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());

        builder.setContentView(R.layout.dialog_about_app);

        builder.findViewById(R.id.shareApp).setOnClickListener(v -> {
            new ShareUtils(getString(R.string.shareAppText), getActivity()).shareApp();
            dismiss();
        });
        builder.findViewById(R.id.ratingApp).setOnClickListener(v -> {
            openIntentGooglePlay();
            dismiss();

        });
        builder.findViewById(R.id.privacyApp).setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pasichdev.github.io/pasich/privacyMyNotes.html"));
            requireContext().startActivity(i);
            dismiss();
        });
        builder.findViewById(R.id.whatUpdate).setOnClickListener(v -> {
            new WhatUpdateDialog().show(getParentFragmentManager(), "WhatsUpdate");
            dismiss();
        });
        return builder;
    }

    private void openIntentGooglePlay() {
        final Uri uri = Uri.parse("market://details?id=" + requireContext().getPackageName());
        final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);
        if (requireContext().getPackageManager().queryIntentActivities(rateAppIntent, 0).size() > 0) {
            startActivity(rateAppIntent);
        } else {
            Toast.makeText(getContext(), getString(R.string.notFoundPlayMarket), Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
