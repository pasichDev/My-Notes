package com.pasich.mynotes.ui.view.dialogs.note;


import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_TEXT_SIZE;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_TEXT_SIZE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.ActivitySettings;
import com.pasich.mynotes.base.view.NoteActivityView;
import com.pasich.mynotes.data.old.notes.Note;
import com.pasich.mynotes.databinding.DialogMoreNoteBinding;
import com.pasich.mynotes.utils.GoogleTranslationIntent;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.ShortCutUtils;
import com.pasich.mynotes.utils.base.simplifications.OnSeekBarChangeListener;
import com.pasich.mynotes.utils.prefences.TextStylePreferences;
import com.preference.PowerPreference;


public class MoreNoteDialog extends DialogFragment {

    private final Note mNote;
    private DialogMoreNoteBinding binding;
    private ActivitySettings activitySettings;
    private NoteActivityView noteActivityView;

    public MoreNoteDialog(Note note) {
        this.mNote = note;
    }

    @SuppressLint("StringFormatMatches")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        builder.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        noteActivityView = (NoteActivityView) getContext();
        activitySettings = (ActivitySettings) getContext();
        binding = DialogMoreNoteBinding.inflate(getLayoutInflater());
        builder.setContentView(binding.getRoot());

        binding.noteInfo.noteInfo.setText(getString(R.string.layoutStringInfoCountSymbols, mNote.getValue().length()));

        initListeners();
        return builder;
    }


    private void initListeners() {
        binding.noSave.setOnClickListener(v -> {
            assert noteActivityView != null;
            noteActivityView.closeActivityNotSaved();
            dismiss();
        });


        if (mNote.getValue().length() >= 2) {
            binding.share.setVisibility(View.VISIBLE);
            binding.share.setOnClickListener(v -> {
                new ShareUtils(mNote, getActivity()).shareNotes();
                dismiss();
            });
            binding.translateNote.setVisibility(View.VISIBLE);
            binding.translateNote.setOnClickListener(v -> {
                new GoogleTranslationIntent().startTranslation(requireActivity(), mNote.getValue());
                dismiss();
            });


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.addShortCutLauncher.setVisibility(View.VISIBLE);
                binding.addShortCutLauncher.setOnClickListener(v -> {
                    ShortCutUtils.createShortCut(mNote, getContext());
                    dismiss();
                });
            }
            binding.moveToTrash.setOnClickListener(v -> {
                assert noteActivityView != null;
                noteActivityView.deleteNote(mNote);
                dismiss();
            });
        }

        binding.changeSettingsVIew.setOnClickListener(v -> {
            binding.groupLayouts.setVisibility(View.GONE);
            binding.viewSettingsNote.rootView.setVisibility(View.VISIBLE);
        });
        changeTextStyle();
        changeTextSize();
    }

    private void changeTextStyle() {
        TextStylePreferences textStyle = new TextStylePreferences(binding.viewSettingsNote.textStyleItem);
        binding.viewSettingsNote.textStyleItem.setOnClickListener(v -> {
            textStyle.changeArgument();
            activitySettings.changeTextStyle();
        });

    }

    private void changeTextSize() {
        SeekBar seekBar = binding.seekbarView.seekBarSize;
        seekBar.setMax(18);
        seekBar.setProgress(PowerPreference.getDefaultFile().getInt(ARGUMENT_PREFERENCE_TEXT_SIZE, ARGUMENT_DEFAULT_TEXT_SIZE) - 12);

        binding.viewSettingsNote.editSizeText.setOnClickListener(v -> {
            binding.viewSettingsNote.rootView.setVisibility(View.GONE);
            binding.seekbarView.rootView.setVisibility(View.VISIBLE);
            binding.seekbarView.valueSeek.setText(String.valueOf(seekBar.getProgress() + 12));
        });

        binding.seekbarView.seekBarSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            protected void changeProgress(int progress) {
                int size = progress + 12;
                binding.seekbarView.valueSeek.setText(String.valueOf(size));
                activitySettings.changeTextSizeOnline(size);
            }

            @Override
            protected void stopChangeProgress(SeekBar seekBar) {
                activitySettings.changeTextSizeOnline(seekBar.getProgress() + 12);
                PowerPreference.getDefaultFile().setInt(ARGUMENT_PREFERENCE_TEXT_SIZE, seekBar.getProgress() + 12);
            }
        });


    }

}
