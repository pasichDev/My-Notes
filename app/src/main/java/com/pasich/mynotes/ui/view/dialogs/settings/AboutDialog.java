package com.pasich.mynotes.ui.view.dialogs.settings;

import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_HOW_TO_USE;
import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_PRIVACY_POLICY;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.activity.TrashActivity;
import com.pasich.mynotes.utils.ShareUtils;

public class AboutDialog extends DialogFragment {

    private final int countTrash;

    public AboutDialog(int countTrash) {
        this.countTrash = countTrash;
    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog builder = new Dialog(requireContext());

        builder.setContentView(R.layout.dialog_about_activity);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        builder.findViewById(R.id.trashActivityLayout).setOnClickListener(v -> {
            if (countTrash > 1) {
                startActivity(new Intent(getActivity(), TrashActivity.class));
            } else {
                Snackbar.make(getActivity().getWindow().getDecorView(),
                        getString(R.string.errorEmptyNotesRestore), BaseTransientBottomBar.LENGTH_LONG).show();

            }
            dismiss();
        });


        builder.findViewById(R.id.privacyApp).setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_PRIVACY_POLICY));
            requireContext().startActivity(i);
            dismiss();
        });

        builder.findViewById(R.id.help).setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_HOW_TO_USE));
            requireContext().startActivity(i);
            dismiss();
        });

        builder.findViewById(R.id.shareApp).setOnClickListener(v -> {
            new ShareUtils(getString(R.string.shareAppText), getActivity()).shareText();
            dismiss();
        });
        builder.findViewById(R.id.ratingApp).setOnClickListener(v -> {
            openIntentGooglePlay();
            dismiss();

        });

        builder.findViewById(R.id.whatUpdate).setOnClickListener(v -> {
            new WhatUpdateDialog().show(getParentFragmentManager(), "WhatsUpdate");
            dismiss();
        });

        builder.findViewById(R.id.feedback).setOnClickListener(v -> {
            new FeedbackDialog().show(getParentFragmentManager(), "WhatsUpdate");
            dismiss();
        });
        builder.findViewById(R.id.restoreOldBackups).setOnClickListener(v -> {
            new RestoreBackupDialog().show(getParentFragmentManager(), "WhatsUpdate");
            dismiss();
        });

        return builder;
    }

    private void openIntentGooglePlay() {
        final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + requireContext().getPackageName()));
        if (requireContext().getPackageManager().queryIntentActivities(rateAppIntent, 0).size() > 0) {
            startActivity(rateAppIntent);
        } else {
            Toast.makeText(getContext(), getString(R.string.notFoundPlayMarket), Toast.LENGTH_SHORT).show();
        }
    }
}
