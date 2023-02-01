package com.pasich.mynotes.ui.view.dialogs.about;

import static com.pasich.mynotes.utils.constants.BackupPreferences.ARGUMENT_AUTO_BACKUP_CLOUD;
import static com.pasich.mynotes.utils.constants.BackupPreferences.ARGUMENT_LAST_BACKUP_ID;
import static com.pasich.mynotes.utils.constants.BackupPreferences.ARGUMENT_LAST_BACKUP_TIME;
import static com.pasich.mynotes.utils.constants.BackupPreferences.FIlE_NAME_PREFERENCE_BACKUP;
import static com.pasich.mynotes.utils.constants.ContactLink.LINK_HOW_TO_USE;
import static com.pasich.mynotes.utils.constants.ContactLink.LINK_PRIVACY_POLICY;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.databinding.DialogAboutActivityBinding;
import com.pasich.mynotes.ui.view.activity.AboutActivity;
import com.pasich.mynotes.ui.view.activity.BackupActivity;
import com.pasich.mynotes.ui.view.activity.TrashActivity;
import com.pasich.mynotes.utils.backup.CloudAuthHelper;
import com.pasich.mynotes.utils.backup.CloudCacheHelper;
import com.pasich.mynotes.utils.constants.DriveScope;
import com.pasich.mynotes.utils.constants.SnackBarInfo;
import com.preference.PowerPreference;
import com.preference.Preference;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AboutDialog extends BaseDialogBottomSheets {
    private final AboutOpensActivity aboutOpensActivity;
    @Inject
    public GoogleSignInClient googleSignInClient;
    @Inject
    public CloudCacheHelper cloudCacheHelper;
    @Inject
    public CloudAuthHelper cloudAuthHelper;
    public DialogAboutActivityBinding binding;
    final private ActivityResultLauncher<Intent> startAuthIntent =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            cloudAuthHelper.getResultAuth(result.getData())
                                    .addOnFailureListener((GoogleSignInAccount) -> onInfoSnack(R.string.errorAuth, null, SnackBarInfo.Error, Snackbar.LENGTH_LONG))
                                    .addOnSuccessListener((GoogleSignInAccount) -> {
                                        cloudCacheHelper.update(GoogleSignInAccount, GoogleSignIn.hasPermissions(GoogleSignInAccount, DriveScope.ACCESS_DRIVE_SCOPE), true);
                                        loadingDataUser(true);
                                    });
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
        initAccountInfo();
        initListeners();
        return builder.create();
    }

    private void initAccountInfo() {
        binding.loginPage.viewLoginRoot.setVisibility(cloudCacheHelper.isInstallPlayMarket() ? View.VISIBLE : View.GONE);
        binding.divider.setVisibility(cloudCacheHelper.isInstallPlayMarket() ? View.VISIBLE : View.GONE);
        loadingDataUser(cloudCacheHelper.isAuth());
    }

    public void initListeners() {

        binding.loginPage.exitUser.setOnClickListener(v -> signOut());

        binding.loginPage.loginUser.setOnClickListener(v -> startAuthIntent.launch(googleSignInClient.getSignInIntent()));

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


    private void loadingDataUser(boolean isAuth) {

        if (isAuth) {
            String nameUser = cloudCacheHelper.getGoogleSignInAccount().getDisplayName();
            binding.loginPage.nameUser.setText(nameUser);
            binding.loginPage.emailUSer.setText(cloudCacheHelper.getGoogleSignInAccount().getEmail());
            Glide.with(requireContext())
                    .load(cloudCacheHelper.getGoogleSignInAccount().getPhotoUrl())
                    .placeholder(new AvatarGenerator.AvatarBuilder(requireContext())
                            .setLabel(nameUser == null ? "User" : nameUser)
                            .setAvatarSize(120)
                            .setTextSize(30)
                            .toSquare()
                            .toCircle()
                            .setBackgroundColor(MaterialColors.getColor(requireContext(), R.attr.colorPrimary, Color.GRAY))
                            .build())

                    .into(binding.loginPage.userAvatar);
            binding.loginPage.loginUser.setVisibility(View.GONE);
            binding.loginPage.loginPageRoot.setVisibility(View.VISIBLE);

        } else {
            binding.loginPage.loginPageRoot.setVisibility(View.GONE);
            binding.loginPage.loginUser.setVisibility(View.VISIBLE);
        }


    }


    // TODO: 20.01.2023 Добавить удаление фоновой службы AutoBackupWorker
    void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            final Preference preference = PowerPreference.getFileByName(FIlE_NAME_PREFERENCE_BACKUP);
            binding.loginPage.loginUser.setVisibility(View.VISIBLE);
            binding.loginPage.loginPageRoot.setVisibility(View.GONE);
            preference.removeAsync(ARGUMENT_AUTO_BACKUP_CLOUD);
            preference.removeAsync(ARGUMENT_LAST_BACKUP_ID);
            preference.removeAsync(ARGUMENT_LAST_BACKUP_TIME);
            cloudCacheHelper.clean();
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
