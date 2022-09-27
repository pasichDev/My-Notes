package com.pasich.mynotes.ui.view.dialogs.settings;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.RestoreNotesBackupOld;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.databinding.DialogRestoreBackupBinding;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RestoreBackupDialog extends BottomSheetDialogFragment {


    private DialogRestoreBackupBinding binding;
    private RestoreNotesBackupOld restoreNotesBackupOld;
    private final ActivityResultLauncher<Intent> openArchiveBackupIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        Intent data = result.getData();
        if (result.getResultCode() == Activity.RESULT_OK) {
            if (data != null) startProcess(data);
        }
    });


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
        binding = DialogRestoreBackupBinding.inflate(getLayoutInflater());
        restoreNotesBackupOld = (RestoreNotesBackupOld) requireContext();

        builder.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        builder.getBehavior().setHideable(false);
        builder.setContentView(binding.getRoot());

        binding.titleView.headTextDialog.setText(getString(R.string.restoreBackup));
        binding.setStep(1);

        binding.openArchive.setOnClickListener(v -> openArchiveBackupIntent.launch(new Intent(Intent.ACTION_OPEN_DOCUMENT).addCategory(Intent.CATEGORY_OPENABLE).setType("application/zip")));

        return builder;
    }


    public void startProcess(Intent data) {
        binding.setStep(2);
        binding.titleView.headTextDialog.setVisibility(View.GONE);
        requireDialog().setCanceledOnTouchOutside(false);
        new Thread(() -> {
            try {
                ParcelFileDescriptor descriptor = requireContext().getContentResolver().openFileDescriptor(data.getData(), "r");
                int count = unzip(descriptor.getFileDescriptor());
                descriptor.close();
                synchronized (this) {
                    if (count == 0) restoreNotesBackupOld.errorProcessRestore();
                    else restoreNotesBackupOld.successfullyProcessRestore(count);
                    dismiss();
                }

            } catch (IOException ignored) {
            }
        }).start();

    }


    public int unzip(FileDescriptor _zipFile) {
        int count = 0;
        try {
            Thread.sleep(2000);
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(_zipFile));
            ZipEntry zipEntry;


            while ((zipEntry = zipInputStream.getNextEntry()) != null) {

                if (zipEntry.getName().endsWith(".txt")) {

                    String[] titleFile = zipEntry.getName().replaceAll(".txt", "").split("/");

                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipInputStream));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                        stringBuilder.append('\n');
                    }
                    if (stringBuilder.length() >= 2) {
                        restoreNotesBackupOld.saveNoteRestore(new Note().create(
                                titleFile.length >= 1 ? titleFile[titleFile.length - 1] : titleFile[0],
                                String.valueOf(stringBuilder), new Date().getTime()));
                        count = count + 1;
                    }
                }

            }

            zipInputStream.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        return count;
    }

}
