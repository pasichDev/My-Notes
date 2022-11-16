package com.pasich.mynotes.ui.helloUI.fragments;


import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.databinding.FragmentFinishBinding;
import com.pasich.mynotes.ui.helloUI.tool.SavesNotes;
import com.pasich.mynotes.ui.view.activity.MainActivity;
import com.preference.PowerPreference;
import com.preference.Preference;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class FinishFragment extends Fragment {

    private final int BUFFER = 80000;
    private boolean finishHello = false;
    private String nameBackup;
    private Handler mHandler;
    private FragmentFinishBinding binding;
    ActivityResultLauncher<Intent> startIntentExport = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                finishSaveBackup(data);

            }
        }
    });
    private SavesNotes savesNotes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savesNotes = (SavesNotes) getContext();
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
        if (finishHello) PowerPreference.getDefaultFile().setBoolean("firstrun", true);

    }


    private void finishSaveBackup(Intent data) {
        ParcelFileDescriptor descriptor;
        try {
            descriptor = requireContext().getContentResolver().openFileDescriptor(data.getData(), "w");
            FileOutputStream outputStream = new FileOutputStream(descriptor.getFileDescriptor());
            copyFile(new File(requireContext().getFilesDir() + "/" + nameBackup), outputStream);
            descriptor.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        binding.backupSave.setVisibility(View.GONE);
        binding.textFinish.setText(R.string.hello_finishTextTwo);
        binding.Finish.setEnabled(true);

    }


    private void deleteOldBackup() {
        final File deleteBackup = new File((requireContext().getFilesDir() + "/" + nameBackup));
        if (deleteBackup.exists()) {
            deleteBackup.delete();
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

        binding.blockFinish.setVisibility(View.GONE);
        binding.textProgress.setVisibility(View.GONE);
        binding.progressLayout.setVisibility(View.VISIBLE);
        mHandler.postDelayed(() -> {
            removesPreferences();
            try {
                searchNotesAndSave();
            } catch (IOException e) {
                Log.wtf(TAG, "onAnimationStart: " + e);
            }
        }, 3000);

    }



    private void removesPreferences() {
        Preference preference = PowerPreference.getDefaultFile();
        preference.remove("spechLaunguage");
        preference.remove("setSpechOutputText");
        preference.remove("autoSave");
        preference.remove("swipeToExit");
        preference.remove("sortPref");
    }


    private void closeHelloTool() {
        finishHello = true;
        requireActivity().finish();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }


    public void searchNotesAndSave() throws IOException {
        File[] files = requireContext().getFilesDir().listFiles();

        // Добавляем файлы
        assert files != null;
        for (File file : files) {
            // Добавим файлы но не папки
            if (!file.isDirectory() && file.getName().endsWith(".txt")) {
                //Здесь записуем только фвйлы с корня
                readFile(file, true, "");
            } else if (file.isDirectory()) {
                if (!file.getName().equals("trash") && !file.getName().equals("VoiceNotes")) {
                    savesNotes.createTag(new Tag().create(file.getName()));
                }
                File[] fileDI = new File(requireContext().getFilesDir() + "/" + file.getName() + "/").listFiles();
                assert fileDI != null;
                for (File fileNameIsDir : fileDI) {
                    if (fileNameIsDir.getName().endsWith(".txt")) {
                        readFile(fileNameIsDir, !file.getName().equals("trash"), file.getName());
                    }
                }

                file.delete();
            }


        }


        closeHelloTool();
    }


    private void readFile(File file, boolean note, String nameTag) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append('\n');
        }
        if (stringBuilder.length() >= 2) {
            if (note)
                savesNotes.saveNote(new Note().create(
                        file.getName().replaceAll(".txt", ""),
                        String.valueOf(stringBuilder), file.lastModified(),
                        nameTag
                ));
            else
                savesNotes.saveTrash(new TrashNote().create(file.getName().replaceAll(".txt", ""), String.valueOf(stringBuilder), file.lastModified()));
        }
        file.delete();
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