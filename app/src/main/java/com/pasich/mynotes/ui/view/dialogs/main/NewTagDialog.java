package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.databinding.DialogNewTagBinding;
import com.pasich.mynotes.utils.ValidateNameTag;

public class NewTagDialog extends BottomSheetDialogFragment {

    private TagsRepository repository;
    private DialogNewTagBinding binding;

    public NewTagDialog(TagsRepository repository) {
        this.repository = repository;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext(), R.style.InputsDialog);
        binding = DialogNewTagBinding.inflate(getLayoutInflater());
        builder.setContentView(binding.getRoot());
        binding.titleInclud.headTextDialog.setText(R.string.addTag);
        binding.includedInput.inputNameTag.requestFocus();
        builder.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        binding.includedInput.saveTag.setOnClickListener(view -> {
            repository.addTag(new Tag().create(binding.includedInput.inputNameTag.getText().toString()));

            dismiss();
        });
        new ValidateNameTag(binding.includedInput.inputNameTag, binding.includedInput.saveTag);
        return builder;
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        repository = null;
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


}
