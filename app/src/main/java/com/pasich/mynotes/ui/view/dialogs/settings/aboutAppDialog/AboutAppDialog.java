package com.pasich.mynotes.ui.view.dialogs.settings.aboutAppDialog;

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

public class AboutAppDialog extends BottomSheetDialogFragment {


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
        final ArrayList<ChoiceModel> arrayChoice = new ArrayList<>();
        final AboutAppView view = new AboutAppView(getLayoutInflater());

        view.setTitle(getString(R.string.aboutApp));

        arrayChoice.add(
                new ChoiceModel(getString(R.string.shareApp), R.drawable.ic_share, "shareApp", false));


        arrayChoice.add(
                new ChoiceModel(getString(R.string.ratingApp), R.drawable.ic_rating, "ratingsApp", false));

        arrayChoice.add(
                new ChoiceModel(getString(R.string.privacyApp), R.drawable.ic_privacy, "privacy", false));


        arrayChoice.add(
                new ChoiceModel(getString(R.string.whatUpdate), R.drawable.ic_about_dialog, "whatsUpdate", false));

        final DialogListAdapter adapter = new DialogListAdapter(arrayChoice);
        view.getItemsView().setAdapter(adapter);

        view.getItemsView()
                .setOnItemClickListener(
                        (parent, v, position, id) -> {
                            if (adapter.getItem(position).getAction().equals("whatsUpdate")) {
                                new WhatUpdateDialog().show(getParentFragmentManager(), "WhatsUpdate");
                            }
                            if (adapter.getItem(position).getAction().equals("privacy")) {
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pasichdev.github.io/pasich/privacyMyNotes.html"));
                                requireContext().startActivity(i);
                            }
                            if (adapter.getItem(position).getAction().equals("shareApp")) {
                                new ShareUtils(getString(R.string.shareAppText), getActivity()).shareApp();
                            }
                            if (adapter.getItem(position).getAction().equals("ratingsApp")) {
                                final Uri uri = Uri.parse("market://details?id=" + requireContext().getPackageName());
                                final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);
                                if (requireContext().getPackageManager().queryIntentActivities(rateAppIntent, 0).size() > 0) {
                                    startActivity(rateAppIntent);
                                } else {
                                    Toast.makeText(getContext(), getString(R.string.notFoundPlayMarket), Toast.LENGTH_SHORT)
                                            .show();
                                }

                            }

                            dismiss();
                        });


        builder.setContentView(view.getRootContainer());

        return builder;
    }
}
