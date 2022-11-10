package com.pasich.mynotes.ui.helloUI.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pasich.mynotes.databinding.FragmentFinishBinding;
import com.pasich.mynotes.ui.helloUI.tool.SavesNotes;
import com.pasich.mynotes.ui.view.activity.MainActivity;
import com.preference.PowerPreference;
import com.preference.Preference;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class FinishFragment extends Fragment {

    private boolean finishHello = false;
    private final int BUFFER = 80000;
    private String nameBackup;
    private Handler mHandler;
    private FragmentFinishBinding binding;
    private SavesNotes savesNotes;
    ActivityResultLauncher<Intent> startIntentExport = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                finishSaveBackup(data);

            }
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // savesNotes = (SavesNotes) getContext();
        mHandler = new Handler(Looper.getMainLooper());
        nameBackup = "MyNotes_Backup_" + new SimpleDateFormat("d_M", Locale.getDefault()).format(new Date().getTime()) + ".zip";
        initBackup();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFinishBinding.inflate(getLayoutInflater(), container, false);
        listeners();
        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        binding.backupSave.setOnClickListener(null);
        binding.Finish.setOnClickListener(null);
        deleteOldBackup();
        if (!finishHello) PowerPreference.getDefaultFile().setBoolean("firstrun", false);

    }


    private void finishSaveBackup(Intent data) {
        ParcelFileDescriptor descriptor;
        try {
            descriptor = requireContext().getContentResolver().openFileDescriptor(data.getData(), "w");
            FileOutputStream outputStream = new FileOutputStream(descriptor.getFileDescriptor());
            copyFile(new File(requireContext().getFilesDir() + "/" + nameBackup), outputStream);
            descriptor.close();
            binding.backupSave.setVisibility(View.GONE);
            binding.Finish.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void deleteOldBackup() {
        if (new File(requireContext().getFilesDir() + "/" + nameBackup).exists()) {
            new File((requireContext().getFilesDir() + "/" + nameBackup)).delete();
        }
    }


    private void initBackup() {
        mHandler.postDelayed(() -> {
            createBackup();
            binding.progressLayout.setVisibility(View.GONE);

            binding.blockFinish.setVisibility(View.VISIBLE);
        }, 3000);
    }


    private void listeners() {
        binding.backupSave.setOnClickListener(v -> saveBackup());
        binding.Finish.setOnClickListener(v -> finishHello());
    }

    private void saveBackup() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/zip");
        intent.putExtra(Intent.EXTRA_TITLE, nameBackup);
        startIntentExport.launch(intent);
    }

    private void finishHello() {
        binding.progressLayout.setVisibility(View.VISIBLE);
        binding.blockFinish.setVisibility(View.GONE);
        binding.textProgress.setVisibility(View.GONE);

        mHandler.postDelayed(() -> {
            removesPreferences();
            saveNotes();

        }, 3000);

    }


    private void removesPreferences() {
        Preference preference = PowerPreference.getDefaultFile();
        preference.remove("spechLaunguage");
        preference.remove("setSpechOutputText");
        preference.remove("autoSave");
        preference.remove("swipeToExit");

    }

    private void saveNotes() {

        mHandler.post(() -> {

            closeHelloTool();

        });

    }

    private void closeHelloTool() {
        finishHello = true;
        requireActivity().finish();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    public void copyFile(@NonNull File source, @NonNull FileOutputStream outputStream) throws IOException {
        try (InputStream inputStream = new FileInputStream(source)) {
            int bufferSize = 1024, length;
            byte[] buffer = new byte[bufferSize];
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            source.delete();
            outputStream.close();
        }
    }


    /**
     * Метод упаковки файлов в zip архив, а именно создание рез. копии
     */
    public void createBackup() {
        ZipOutputStream out = null;
        File[] files = requireContext().getFilesDir().listFiles();
        int countFIles = 0;
        byte[] data = new byte[BUFFER];

        try {
            out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(requireContext().getFilesDir() + "/" + nameBackup)));
            // Добавляем файлы
            assert files != null;
            for (File file : files) {
                // Добавим файлы но не папки
                if (!file.isDirectory() && file.getName().endsWith(".txt")) {
                    processFile(out, data, file.getName());
                    countFIles = countFIles + 1;
                }

                // Добавим папки и подпапки
                if (file.isDirectory()) {
                    File[] fileDI = new File(requireContext().getFilesDir() + "/" + file.getName() + "/").listFiles();
                    assert fileDI != null;
                    for (File fileNameIsDir : fileDI) {
                        if (fileNameIsDir.getName().endsWith(".txt")) {
                            processFile(out, data, file.getName() + "/" + fileNameIsDir.getName());
                            countFIles = countFIles + 1;
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(out);
        }

    }


    /**
     * Метод который пакует файлы в zip
     *
     * @param out  - адрес zip архива в который пакуем
     * @param data - Размер буфера
     * @param file - файл который добавляем в рахив
     */
    private void processFile(ZipOutputStream out, byte[] data, String file) {
        BufferedInputStream origin = null;
        try {
            origin = new BufferedInputStream(new FileInputStream(requireContext().getFilesDir() + "/" + file), BUFFER);
            out.putNextEntry(new ZipEntry(file));
            int count;
            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(origin);
        }
    }


    /**
     * Закрытие Streem
     */
    private void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }

}