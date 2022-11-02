package com.pasich.mynotes.ui.view.dialogs.note;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.base.view.MoreNoteDialogView;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.databinding.DialogMoreNoteBinding;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.MoreNoteDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.MoreNoteDialogPresenter;
import com.pasich.mynotes.utils.GoogleTranslationIntent;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.ShortCutUtils;
import com.pasich.mynotes.utils.base.simplifications.OnSeekBarChangeListener;
import com.pasich.mynotes.utils.prefences.TextStylePreferences;

import javax.inject.Inject;


public class MoreNoteDialog extends BaseDialogBottomSheets implements MoreNoteDialogContract.view {


    private final Note mNote;
    private final boolean newNoteActivity;
    private final int PREF_SIZE_TEXT = 12;
    @Inject
    public MoreNoteDialogPresenter mPresenter;
    private DialogMoreNoteBinding binding;
    private MoreNoteDialogView activitySettings;
    private TextStylePreferences textStylePreferences;


    public MoreNoteDialog(Note note, boolean newNoteActivity) {
        this.mNote = note;
        this.newNoteActivity = newNoteActivity;
    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activitySettings = (MoreNoteDialogView) getContext();
        binding = DialogMoreNoteBinding.inflate(getLayoutInflater());
        textStylePreferences = new TextStylePreferences(binding.settingsActivity.textStyleItem);
        requireDialog().setContentView(binding.getRoot());
        final ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.attachView(this);
            mPresenter.viewIsReady();
        } else {
            dismiss();
        }

        binding.includeHead.headTextDialog.setText(mNote.getTitle().length() != 0 ? mNote.getTitle() : getString(R.string.chooseNote));
        binding.setNewNote(newNoteActivity);
        binding.setValuesText(mNote.getValue().length() > 5);
        binding.noteInfo.noteInfo.setText(getString(R.string.layoutStringInfoCountSymbols, String.valueOf(mNote.getValue().length())));


        return requireDialog();
    }

    @Override
    public void setSeekBarValue(int value) {
        binding.settingsActivity.seekBarSize.setMax(18);
        binding.settingsActivity.seekBarSize.setProgress(value - PREF_SIZE_TEXT);
    }

    @Override
    public void initListeners() {
        binding.noSave.setOnClickListener(v -> activitySettings.closeActivityNotSaved());


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
                mPresenter.deleteNote(mNote);
                dismiss();
            });
        }


        binding.settingsActivity.textStyleItem.setOnClickListener(v -> {
            textStylePreferences.changeArgument();
            activitySettings.changeTextStyle();
        });


        binding.settingsActivity.seekBarSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            protected void changeProgress(int progress) {
                int size = progress + PREF_SIZE_TEXT;
                binding.settingsActivity.valueSeek.setText(String.valueOf(size));
                activitySettings.changeTextSizeOnline(size);
            }

            @Override
            protected void stopChangeProgress(SeekBar seekBar) {
                activitySettings.changeTextSizeOnline(seekBar.getProgress() + PREF_SIZE_TEXT);
                mPresenter.editSizeText(seekBar.getProgress() + PREF_SIZE_TEXT);
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mPresenter.destroy();
        binding.noSave.setOnClickListener(null);
        binding.share.setOnClickListener(null);
        binding.translateNote.setOnClickListener(null);
        binding.addShortCutLauncher.setOnClickListener(null);
        binding.moveToTrash.setOnClickListener(null);
        binding.settingsActivity.seekBarSize.setOnSeekBarChangeListener(null);
        binding.settingsActivity.textStyleItem.setOnClickListener(null);
    }


}
