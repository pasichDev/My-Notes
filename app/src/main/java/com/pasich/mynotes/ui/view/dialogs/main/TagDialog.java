package com.pasich.mynotes.ui.view.dialogs.main;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_NAME_TAG;
import static com.pasich.mynotes.utils.constants.TagSettings.MAX_TAG_COUNT;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.databinding.DialogAddTagToNoteBinding;
import com.pasich.mynotes.ui.contract.dialogs.TagDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.TagDialogPresenter;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class TagDialog extends BaseDialogBottomSheets implements TagDialogContract.view {

    private final Note note;
    private final TagDialogPresenter dialogPresenter;  // @Inject
    public BottomSheetDialog builder; // @Inject
    private DialogAddTagToNoteBinding binding;
    private boolean errorText = true;


    public TagDialog(Note note) {
        this.note = note;
        this.dialogPresenter = null;

    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new BottomSheetDialog(requireContext());
        binding = DialogAddTagToNoteBinding.inflate(getLayoutInflater());
        builder.setContentView(binding.getRoot());


        initTitle();
        binding.chipGroupSystem.setVisibility(View.VISIBLE);
        dialogPresenter.attachView(this);
        dialogPresenter.viewIsReady();
        return builder;
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        dialogPresenter.detachView();
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void initTitle() {
        binding.includeHead.headTextDialog.setText(note.getTag().length() == 0 ? getString(R.string.selectTagForNote) : getString(R.string.editSelectTagForNote));

    }

    @Override
    public void loadingTagsOfChips(List<Tag> tagsList) {
        for (Tag tag : tagsList) {
            if (!note.getTag().equals(tag.getNameTag())) createChipTag(tag.getNameTag());
        }
    }


    @Override
    public void initListeners() {
        try {
            if (dialogPresenter.getCountTags() < MAX_TAG_COUNT) {
                binding.addTagChip.setVisibility(View.VISIBLE);
                binding.addTagChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    binding.includedInput.getRoot().setVisibility(View.VISIBLE);
                    binding.includedInput.outlinedTextField.requestFocus();
                    binding.includedInput.outlinedTextField.setEndIconOnClickListener(v -> saveTag());
                    binding.includedInput.nameTag.setOnEditorActionListener((v, actionId, event) -> {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            saveTag();
                            return true;
                        }
                        return false;
                    });

                    binding.includedInput.nameTag.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            validateText(s.toString().trim().length());
                        }
                    });


                    binding.addTagChip.setVisibility(View.GONE);

                });
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (note.getTag().length() >= 2) {
            binding.dellTagChip.setVisibility(View.VISIBLE);
            binding.dellTagChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                dialogPresenter.removeTagNote(note.getId());
                dismiss();
            });
        }

    }


    private void selectedTag(String nameChip) {
        if (!nameChip.equals(note.getTag())) {
            dialogPresenter.editTagNote(nameChip, note.getId());
            dismiss();
        }
    }

    private void validateText(int length) {
        if (length >= MAX_NAME_TAG) {
            errorText = true;
            binding.includedInput.outlinedTextField.setError(getString(R.string.errorNewTagInput, MAX_NAME_TAG));
        } else if (length == MAX_NAME_TAG - 1) {
            errorText = false;
            binding.includedInput.outlinedTextField.setError(null);
        }
        if (length < 1) errorText = true;
        else if (length < MAX_NAME_TAG - 1) errorText = false;
    }


    private void saveTag() {
        if (!errorText) {
            dialogPresenter.createTagNote(new Tag().create(Objects.requireNonNull(binding.includedInput.nameTag.getText()).toString()), note.getId());
            dismiss();
        }
    }

    private void createChipTag(String nameChip) {
        Chip newChip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip_entry, binding.chipGroupSystem, false);
        newChip.setText(nameChip);
        binding.chipGroupSystem.addView(newChip);
        newChip.setOnCheckedChangeListener((buttonView, isChecked) -> selectedTag(nameChip));
    }
}
