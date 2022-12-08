package com.pasich.mynotes.ui.view.dialogs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.chip.Chip;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.base.simplifications.OnSeekBarChangeListener;
import com.pasich.mynotes.base.view.MoreNoteMainActivityView;
import com.pasich.mynotes.base.view.MoreNoteNoteActivityView;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.databinding.DialogMoreNoteBinding;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.MoreNoteDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.MoreNoteDialogPresenter;
import com.pasich.mynotes.utils.GoogleTranslationIntent;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.tool.TextStyleTool;

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
    public TextStyleTool textStylePreferences;
    private int positionItem;
    private DialogMoreNoteBinding binding;
    /**
     * Interfaces
     */
    private MoreNoteNoteActivityView noteActivity;
    private MoreNoteMainActivityView mainActivity;

    public MoreNoteDialog(Note note, boolean newNoteActivity, boolean activityNote, int position) {
        this.mNote = note;
        this.newNoteActivity = newNoteActivity;
        this.activityNote = activityNote;
        this.positionItem = position;
    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        vibrateOpenDialog(true);
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

        addTitle();
        binding.settingsActivity.rootView.setVisibility(activityNote ? View.VISIBLE : View.GONE);

        return requireDialog();
    }

    public void addTitle() {
        binding.includeHead.headTextDialog.setText(mNote.getTitle().length() > 1 ? mNote.getTitle() : getString(R.string.chooseNote));
        binding.includeHead.getRoot().setVisibility(newNoteActivity ? View.GONE : View.VISIBLE);


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
        mPresenter.getCompositeDisposable().add(tagsList.subscribeOn(mPresenter.getSchedulerProvider().io()).observeOn(mPresenter.getSchedulerProvider().ui()).subscribe(this::createChipsTag));


    }

    public void setRippleBottomLayout() {
        if (binding.chipGroupSystem.getChildCount() == 0 && !newNoteActivity) {
            if (activityNote)
                binding.noSave.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.item_bottom_ripple));
            else
                binding.moveToTrash.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.item_bottom_ripple));

        } else if (newNoteActivity) {
            binding.noSave.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.item_bottom_new_ripple));
        }
    }

    @Override
    public void initInterfaces() {
        if (activityNote) {
            noteActivity = (MoreNoteNoteActivityView) getContext();
            mainActivity = null;
        } else {
            mainActivity = (MoreNoteMainActivityView) getContext();
            noteActivity = null;
        }
    }

    @Override
    public void callableCopyNote(long newNoteId) {
        if (activityNote) {
            noteActivity.openCopyNote(Math.toIntExact(newNoteId));
        } else {
            mainActivity.openCopyNote(Math.toIntExact(newNoteId));
        }
    }


    @Override
    public void initListeners() {

        if (activityNote) {
            binding.noSave.setOnClickListener(v -> noteActivity.closeActivityNotSaved());
            binding.settingsActivity.seekBarSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                @Override
                protected void changeProgress(int progress) {
                    int size = progress + PREF_SIZE_TEXT;
                    binding.settingsActivity.valueSeek.setText(String.valueOf(size));
                    noteActivity.changeTextSizeOnline(size);
                }

                @Override
                protected void stopChangeProgress(SeekBar seekBar) {
                    noteActivity.changeTextSizeOnline(seekBar.getProgress() + PREF_SIZE_TEXT);
                    mPresenter.editSizeText(seekBar.getProgress() + PREF_SIZE_TEXT);
                }
            });
            binding.settingsActivity.textStyleItem.setOnClickListener(v -> {
                textStylePreferences.changeArgument();
                noteActivity.changeTextStyle();
            });

        } else {
            binding.actionPanelActivate.setOnClickListener(view -> {
                assert mainActivity != null;
                mainActivity.actionStartNote(mNote, positionItem);
                dismiss();
            });
        }


        if (mNote.getValue().length() >= 2) {
            binding.share.setVisibility(View.VISIBLE);
            binding.share.setOnClickListener(v -> {
                ShareUtils.shareNotes(requireActivity(), mNote.getValue());
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
                    new CreateShortcutDialog(mNote).show(getParentFragmentManager(), "CreateDialogShortCut");
                    dismiss();
                });
            }
            binding.moveToTrash.setOnClickListener(v -> {
                mPresenter.deleteNote(mNote);

                if (!activityNote) {
                    mainActivity.callbackDeleteNote(mNote);
                    dismiss();
                } else {
                    noteActivity.closeActivityNotSaved();
                }

            });

            binding.copyNote.setOnClickListener(v -> {
                mPresenter.copyNote(mNote, activityNote);
                dismiss();
            });
        }

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        mPresenter.detachView();
        if (activityNote) {
            binding.noSave.setOnClickListener(null);
            binding.translateNote.setOnClickListener(null);
            binding.settingsActivity.seekBarSize.setOnSeekBarChangeListener(null);
            binding.settingsActivity.textStyleItem.setOnClickListener(null);
            noteActivity = null;
        } else {
            mainActivity = null;
            binding.actionPanelActivate.setOnClickListener(null);
            positionItem = 0;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            binding.addShortCutLauncher.setOnClickListener(null);
        }
        binding.moveToTrash.setOnClickListener(null);
        binding.copyNote.setOnClickListener(null);
        binding.share.setOnClickListener(null);
    }


    private void createChipsTag(List<Tag> tags) {
        if (tags.size() != 0) {
            for (Tag tag : tags) {

                Chip newChip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip_entry, binding.chipGroupSystem, false);
                newChip.setText(getString(R.string.tagHastag, tag.getNameTag()));
                if (mNote.getTag().equals(tag.getNameTag())) {
                    newChip.setChecked(true);
                    binding.chipGroupSystem.addView(newChip, 0);
                } else {
                    binding.chipGroupSystem.addView(newChip);
                }

                newChip.setOnCheckedChangeListener(((buttonView, isChecked) -> selectedTag(tag.getNameTag(), isChecked)));
            }
        } else {
            binding.scrollChips.setVisibility(View.GONE);
            setRippleBottomLayout();
        }

    }


    private void selectedTag(String nameChip, boolean checked) {
        if (checked) {

            mPresenter.editTagNote(nameChip, mNote.getId());
            if (activityNote) noteActivity.changeTag(nameChip);
        } else {

            mPresenter.removeTagNote(mNote.getId());
            if (activityNote) noteActivity.changeTag("");
        }
    }


}
