package com.pasich.mynotes.ui.view.dialogs.settings.aboutDialog;

import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_HOW_TO_USE;
import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_PRIVACY_POLICY;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.DialogAboutActivityBinding;
import com.pasich.mynotes.ui.view.activity.AboutActivity;
import com.pasich.mynotes.ui.view.activity.BackupActivity;
import com.pasich.mynotes.ui.view.activity.TrashActivity;

import java.util.Objects;

public class AboutDialog extends DialogFragment {
    private GoogleSignInClient gsc;
    private DialogAboutActivityBinding binding;
    private final AboutOpensActivity aboutOpensActivity;

    final private ActivityResultLauncher<Intent> startAuthIntent =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        Intent data = result.getData();
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            try {
                                task.getResult(ApiException.class);
                                loadingDataUser(Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(requireContext())));
                                binding.loginPage.loginUser.setVisibility(View.GONE);
                                binding.loginPage.loginPageRoot.setVisibility(View.VISIBLE);
                            } catch (ApiException e) {
                                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


    public AboutDialog(AboutOpensActivity aboutOpensActivity) {
        this.aboutOpensActivity = aboutOpensActivity;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        binding = DialogAboutActivityBinding.inflate(getLayoutInflater());
        builder.setView(binding.getRoot());
        gsc = GoogleSignIn.getClient(requireContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build());

        initDialog();
        initListeners();
        return builder.create();
    }

    private void initDialog() {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(requireContext());
        if (acct != null) {
            binding.loginPage.loginUser.setVisibility(View.GONE);
            loadingDataUser(acct);
        } else {
            binding.loginPage.loginPageRoot.setVisibility(View.GONE);
            binding.loginPage.loginUser.setVisibility(View.VISIBLE);
        }


    }

    private void initListeners() {

        binding.loginPage.exitUser.setOnClickListener(v -> signOut());

        binding.loginPage.loginUser.setOnClickListener(v -> startAuthIntent.launch(gsc.getSignInIntent()));

        binding.trashActivityLayout.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), TrashActivity.class));
            dismiss();
        });
        binding.backups.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), BackupActivity.class));
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
    }


    private void loadingDataUser(GoogleSignInAccount acct) {
        binding.loginPage.nameUser.setText(acct.getDisplayName());
        binding.loginPage.emailUSer.setText(acct.getEmail());
        Glide.with(requireContext())
                .load(acct.getPhotoUrl())
                .placeholder(R.drawable.demo_avatar_user)
                .error(R.drawable.demo_avatar_user)
                .into(binding.loginPage.userAvatar);
    }


    void signOut() {
        gsc.signOut().addOnCompleteListener(task -> {
            binding.loginPage.loginUser.setVisibility(View.VISIBLE);
            binding.loginPage.loginPageRoot.setVisibility(View.GONE);

        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        binding.trashActivityLayout.setOnClickListener(null);

        binding.loginPage.exitUser.setOnClickListener(null);

        binding.loginPage.loginUser.setOnClickListener(null);
        binding.privacyApp.setOnClickListener(null);
        binding.backups.setOnClickListener(null);
        binding.help.setOnClickListener(null);
        binding.aboutApp.setOnClickListener(null);
        binding.themeApp.setOnClickListener(null);
    }
}
