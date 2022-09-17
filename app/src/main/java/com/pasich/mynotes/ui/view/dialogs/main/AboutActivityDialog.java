package com.pasich.mynotes.ui.view.dialogs.main;

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

import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.activity.TrashActivity;
import com.pasich.mynotes.ui.view.dialogs.settings.WhatUpdateDialog;
import com.pasich.mynotes.utils.ShareUtils;

public class AboutActivityDialog extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog builder = new Dialog(requireContext());

        builder.setContentView(R.layout.dialog_about_activity);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        builder.findViewById(R.id.trashActivityLayout).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), TrashActivity.class));
            dismiss();
        });


        builder.findViewById(R.id.privacyApp).setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_PRIVACY_POLICY));
            requireContext().startActivity(i);
            dismiss();
        });

        builder.findViewById(R.id.howUseApp).setOnClickListener(v -> {
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
