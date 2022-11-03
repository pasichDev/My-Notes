package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.chip.Chip;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.base.view.ChoiceNoteView;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.databinding.DialogChoiceNoteBinding;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.ChoiceNoteDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.ChoiceNoteDialogPresenter;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.ShortCutUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class ChoiceNoteDialog extends BaseDialogBottomSheets implements ChoiceNoteDialogContract.view {

    @Inject
    public ChoiceNoteDialogPresenter mPresenter;
    private Note note;
    private int positionItem;
    private DialogChoiceNoteBinding binding;
    private ChoiceNoteView noteView;

    public ChoiceNoteDialog(Note note, int position) {
        this.note = note;
        this.positionItem = position;


    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        noteView = (ChoiceNoteView) getContext();
        binding = DialogChoiceNoteBinding.inflate(getLayoutInflater());
        requireDialog().setContentView(binding.getRoot());
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.attachView(this);
            mPresenter.viewIsReady();
        } else {
            dismiss();
        }
        binding.includeHead.headTextDialog.setText(note.getTitle().length() != 0 ? note.getTitle() : getString(R.string.chooseNote));
        binding.deleteTagForNote.setVisibility(note.getTag().length() >= 2 ? View.VISIBLE : View.GONE);
        return requireDialog();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mPresenter.detachView();
        positionItem = 0;
        note = null;
        binding.actionPanelActivate.setOnClickListener(null);
        binding.shareLinearLayout.setOnClickListener(null);
        binding.moveToTrash.setOnClickListener(null);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            binding.addNoteForDesktop.setOnClickListener(null);
    }

    @Override
    public void loadingTagsOfChips(Flowable<List<Tag>> tagsList) {
        mPresenter.getCompositeDisposable().add(
                tagsList.subscribeOn(mPresenter.getSchedulerProvider().io())
                        .subscribe(tags -> requireActivity().runOnUiThread(() -> createChipsTag(tags)))
        );


    }

    private void createChipsTag(List<Tag> tags) {
        for (Tag tag : tags) {
            if (!note.getTag().equals(tag.getNameTag())) {
                Chip newChip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip_entry, binding.chipGroupSystem, false);
                newChip.setText(getString(R.string.tagHastag, tag.getNameTag()));
                binding.chipGroupSystem.addView(newChip);
                newChip.setOnClickListener(view -> selectedTag(tag.getNameTag()));
            }
        }


    }


    private void selectedTag(String nameChip) {
        if (!nameChip.equals(note.getTag())) {
            mPresenter.editTagNote(nameChip, note.getId());
            dismiss();
        }
    }


    @Override
    public void initListeners() {
        binding.actionPanelActivate.setOnClickListener(view -> {
            assert noteView != null;
            noteView.actionStartNote(note, positionItem);
            dismiss();
        });
        binding.shareLinearLayout.setOnClickListener(view -> {
            new ShareUtils(note, getActivity()).shareNotes();
            dismiss();
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            binding.addNoteForDesktop.setVisibility(View.VISIBLE);
            binding.addNoteForDesktop.setOnClickListener(view -> {
                ShortCutUtils.createShortCut(note, getContext());
                dismiss();
            });
        }

        binding.moveToTrash.setOnClickListener(view -> {
            mPresenter.deleteNote(note);
            dismiss();
        });
        binding.deleteTagForNote.setOnClickListener(view -> {
            mPresenter.removeTagNote(note.getId());
            dismiss();
        });

    }

}
