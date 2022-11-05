package com.pasich.mynotes.ui.view.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

import com.google.android.material.chip.Chip;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.base.view.ChoiceNoteView;
import com.pasich.mynotes.base.view.MoreNoteDialogView;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.databinding.DialogMoreNoteBinding;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.MoreNoteDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.MoreNoteDialogPresenter;
import com.pasich.mynotes.utils.GoogleTranslationIntent;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.ShortCutUtils;
import com.pasich.mynotes.utils.base.simplifications.OnSeekBarChangeListener;
import com.pasich.mynotes.utils.prefences.TextStylePreferences;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;


public class MoreNoteDialog extends BaseDialogBottomSheets implements MoreNoteDialogContract.view {


    private final Note mNote;
    private final boolean newNoteActivity;
    private final int PREF_SIZE_TEXT = 12;
    private final boolean activityNote;
    @Inject
    public MoreNoteDialogPresenter mPresenter;
    @Inject
    public TextStylePreferences textStylePreferences;
    private int positionItem;
    private DialogMoreNoteBinding binding;

    /**
     * Interfaces
     */
    private MoreNoteDialogView activitySettings;
    private ChoiceNoteView noteView;

    public MoreNoteDialog(Note note, boolean newNoteActivity, boolean activityNote, int position) {
        this.mNote = note;
        this.newNoteActivity = newNoteActivity;
        this.activityNote = activityNote;
        this.positionItem = position;
    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogMoreNoteBinding.inflate(getLayoutInflater());
        requireDialog().setContentView(binding.getRoot());

        final ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.attachView(this);
            mPresenter.viewIsReady();
            binding.setNewNote(newNoteActivity);
            binding.setActivityNote(activityNote);
            binding.setValuesText(mNote.getValue().length() > 5);
            textStylePreferences.addButton(binding.settingsActivity.textStyleItem);
        } else {
            dismiss();
        }

        binding.includeHead.headTextDialog.setText(mNote.getTitle().length() > 1 ? mNote.getTitle() : getString(R.string.chooseNote));
        binding.includeHead.headTextDialog.setVisibility(newNoteActivity ? View.GONE : View.VISIBLE);
        binding.settingsActivity.rootView.setVisibility(activityNote ? View.VISIBLE : View.GONE);

        return requireDialog();
    }


    @Override
    public void setSeekBarValue(int value) {
        if (activityNote) {
            binding.settingsActivity.seekBarSize.setMax(18);
            binding.settingsActivity.seekBarSize.setProgress(value - PREF_SIZE_TEXT);
        }
    }

    @Override
    public void loadingTagsOfChips(Flowable<List<Tag>> tagsList) {
        mPresenter.getCompositeDisposable().add(tagsList.subscribeOn(mPresenter.getSchedulerProvider().io()).subscribe(tags -> requireActivity().runOnUiThread(() -> createChipsTag(tags))));

    }

    @Override
    public void initInterfaces() {
        if (activityNote) {
            activitySettings = (MoreNoteDialogView) getContext();
            noteView = null;
        } else {
            noteView = (ChoiceNoteView) getContext();
            activitySettings = null;
        }
    }

    @Override
    public void initListeners() {

        if (activityNote) {
            binding.noSave.setOnClickListener(v -> activitySettings.closeActivityNotSaved());
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
            binding.settingsActivity.textStyleItem.setOnClickListener(v -> {
                textStylePreferences.changeArgument();
                activitySettings.changeTextStyle();
            });

        } else {
            binding.actionPanelActivate.setOnClickListener(view -> {
                assert noteView != null;
                noteView.actionStartNote(mNote, positionItem);
                dismiss();
            });
        }


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
                if (!activityNote) dismiss();
                else {
                    activitySettings.closeActivityNotSaved();
                }
            });
        }

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (activityNote) {
            binding.noSave.setOnClickListener(null);
            binding.translateNote.setOnClickListener(null);
            binding.settingsActivity.seekBarSize.setOnSeekBarChangeListener(null);
            binding.settingsActivity.textStyleItem.setOnClickListener(null);
            activitySettings = null;
        } else {
            noteView = null;
            binding.actionPanelActivate.setOnClickListener(null);
            positionItem = 0;
        }
        mPresenter.destroy();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            binding.addShortCutLauncher.setOnClickListener(null);
        }
        binding.moveToTrash.setOnClickListener(null);
        binding.share.setOnClickListener(null);
    }


    private void createChipsTag(List<Tag> tags) {
        if (tags.size() != 0) {
            for (Tag tag : tags) {
                if (!mNote.getTag().equals(tag.getNameTag())) {
                    Chip newChip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip_entry, binding.chipGroupSystem, false);
                    newChip.setText(getString(R.string.tagHastag, tag.getNameTag()));
                    binding.chipGroupSystem.addView(newChip);
                    newChip.setOnClickListener(view -> selectedTag(tag.getNameTag()));
                }
            }
        } else {
            binding.chipGroupSystem.setVisibility(View.GONE);
        }

    }


    private void selectedTag(String nameChip) {
        if (!nameChip.equals(mNote.getTag())) {

            mPresenter.editTagNote(nameChip, mNote.getId());
            if (activityNote) activitySettings.changeTag(nameChip);
            dismiss();
        }
    }


}
