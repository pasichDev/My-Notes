package com.pasich.mynotes.ui.view.dialogs.settings.aboutDialog;

import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_AUTO_BACKUP_CLOUD;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.Backup_Constants.ARGUMENT_LAST_BACKUP_TIME;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.api.services.drive.DriveScopes;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.databinding.DialogAboutActivityBinding;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.view.activity.AboutActivity;
import com.pasich.mynotes.ui.view.activity.BackupActivity;
import com.pasich.mynotes.ui.view.activity.TrashActivity;
import com.preference.PowerPreference;
import com.preference.Preference;

import java.util.Objects;


public class AboutDialog extends BaseDialogBottomSheets {

    public GoogleSignInClient mGsic;
    public DialogAboutActivityBinding binding;
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
        final ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            binding = DialogAboutActivityBinding.inflate(getLayoutInflater());
            builder.setView(binding.getRoot());
            initAccountInfo();
            initListeners();
        } else {
            dismiss();
        }

        mGsic = GoogleSignIn.getClient(requireContext(), new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_APPDATA))
                .build());
        return builder.create();
    }

    private void initAccountInfo() {
        GoogleSignInAccount mAcct = GoogleSignIn.getLastSignedInAccount(requireContext());
        if (mAcct != null) {
            binding.loginPage.loginUser.setVisibility(View.GONE);
            loadingDataUser(mAcct);
        } else {
            binding.loginPage.loginPageRoot.setVisibility(View.GONE);
            binding.loginPage.loginUser.setVisibility(View.VISIBLE);
        }


    }

    public void initListeners() {

        binding.loginPage.exitUser.setOnClickListener(v -> signOut());

        binding.loginPage.loginUser.setOnClickListener(v -> startAuthIntent.launch(mGsic.getSignInIntent()));

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
        mGsic.signOut().addOnCompleteListener(task -> {
            final Preference preference = PowerPreference.getFileByName("lastBackupCloudInfo");
            binding.loginPage.loginUser.setVisibility(View.VISIBLE);
            binding.loginPage.loginPageRoot.setVisibility(View.GONE);
            preference.remove(ARGUMENT_AUTO_BACKUP_CLOUD);
            preference.remove(ARGUMENT_LAST_BACKUP_ID);
            preference.remove(ARGUMENT_LAST_BACKUP_TIME);
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
