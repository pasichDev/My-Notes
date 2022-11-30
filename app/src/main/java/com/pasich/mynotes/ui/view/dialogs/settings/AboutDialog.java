package com.pasich.mynotes.ui.view.dialogs.settings;

import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_HOW_TO_USE;
import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_PRIVACY_POLICY;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.DialogAboutActivityBinding;
import com.pasich.mynotes.ui.view.activity.TrashActivity;
import com.pasich.mynotes.utils.ShareUtils;

public class AboutDialog extends DialogFragment {

    private DialogAboutActivityBinding binding;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        binding = DialogAboutActivityBinding.inflate(getLayoutInflater());
        builder.setView(binding.getRoot());
        //  builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        binding.trashActivityLayout.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), TrashActivity.class));
            dismiss();
        });


        binding.privacyApp.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_PRIVACY_POLICY));
            requireContext().startActivity(i);
            dismiss();
        });

        binding.help.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_HOW_TO_USE));
            requireContext().startActivity(i);
            dismiss();
        });

        binding.shareApp.setOnClickListener(v -> {
            new ShareUtils(getString(R.string.shareAppText), getActivity()).shareText();
            dismiss();
        });
        binding.ratingApp.setOnClickListener(v -> {
            openIntentGooglePlay();
            dismiss();

        });

        binding.whatUpdate.setOnClickListener(v -> {
            new WhatUpdateDialog().show(getParentFragmentManager(), "WhatsUpdate");
            dismiss();
        });

        binding.feedback.setOnClickListener(v -> {
            new FeedbackDialog().show(getParentFragmentManager(), "WhatsUpdate");
            dismiss();
        });
        binding.restoreOldBackups.setOnClickListener(v -> {
            new RestoreBackupDialog().show(getParentFragmentManager(), "WhatsUpdate");
            dismiss();
        });

        return builder.create();
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
