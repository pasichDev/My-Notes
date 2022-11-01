package com.pasich.mynotes.ui.view.dialogs.main;

import static com.pasich.mynotes.utils.FormattedDataUtil.lastDayEditNote;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.base.view.ChoiceNoteView;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.databinding.DialogChoiceNoteBinding;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.ChoiceNoteDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.ChoiceNoteDialogPresenter;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.ShortCutUtils;

import javax.inject.Inject;

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

        binding.includeInfo.noteInfo.setText(getString(R.string.layoutStringInfo, lastDayEditNote(note.getDate()), String.valueOf(note.getValue().length())));
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
        binding.tagLinearLayout.setOnClickListener(null);
        binding.moveToTrash.setOnClickListener(null);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            binding.addNoteForDesktop.setOnClickListener(null);
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

        binding.tagLinearLayout.setOnClickListener(view -> {
            new TagDialog(note).show(getChildFragmentManager(), "TagEditDialog");
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
    }

}
