package com.pasich.mynotes.ui.view.dialogs.settings.aboutDialog;

import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_HOW_TO_USE;
import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_PRIVACY_POLICY;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.pasich.mynotes.databinding.DialogAboutActivityBinding;
import com.pasich.mynotes.ui.view.activity.AboutActivity;
import com.pasich.mynotes.ui.view.activity.TrashActivity;

public class AboutDialog extends DialogFragment {

    private DialogAboutActivityBinding binding;
    private final AboutOpensActivity aboutOpensActivity;

    public AboutDialog(AboutOpensActivity aboutOpensActivity) {
        this.aboutOpensActivity = aboutOpensActivity;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        binding = DialogAboutActivityBinding.inflate(getLayoutInflater());
        builder.setView(binding.getRoot());

        binding.trashActivityLayout.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), TrashActivity.class));
            dismiss();
        });


        binding.privacyApp.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_PRIVACY_POLICY));
            requireContext().startActivity(i);
            dismiss();
        });

        binding.themeApp.setOnClickListener(v -> {
            aboutOpensActivity.openThemeActivity();
            dismiss();
        });

        binding.help.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_HOW_TO_USE));
            requireContext().startActivity(i);
            dismiss();
        });

        binding.aboutApp.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AboutActivity.class);
            requireContext().startActivity(i);
            dismiss();
        });

        return builder.create();
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        binding.trashActivityLayout.setOnClickListener(null);


        binding.privacyApp.setOnClickListener(null);

        binding.help.setOnClickListener(null);
        binding.aboutApp.setOnClickListener(null);
        binding.themeApp.setOnClickListener(null);
    }
}
