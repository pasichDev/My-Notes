package com.pasich.mynotes.ui.view.dialogs.main;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_NAME_TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.databinding.DialogNewTagBinding;
import com.pasich.mynotes.utils.simplifications.TextValidatorUtils;

public class NewTagDialog extends BottomSheetDialogFragment {

    private TagsRepository repository;
    private DialogNewTagBinding binding;

    public NewTagDialog(TagsRepository repository) {
        this.repository = repository;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        binding = DialogNewTagBinding.inflate(getLayoutInflater());

        builder.setContentView(binding.getRoot());

        MaterialTextView title = builder.findViewById(R.id.headTextDialog);
        assert title != null;
        title.setText(R.string.addTag);


        binding.setErrorText(false);
        binding.setEnableButtonSave(false);
        binding.inputNameTag.requestFocus();

        builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ((InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);


        binding.saveTag.setOnClickListener(
                view -> {
                    repository.addTag(new Tag().create(binding.inputNameTag.getText().toString()));
                    dismiss();
                });


        binding.inputNameTag.addTextChangedListener(
                new TextValidatorUtils(binding.inputNameTag) {
                    @Override
                    public void validate(TextView textView, String text) {
                        validateText(text.trim().length());
                    }
                });

        return builder;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        repository = null;
        requireActivity()
                .getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void validateText(int length) {
        if (length >= MAX_NAME_TAG) {
            binding.setErrorText(true);
            binding.setEnableButtonSave(false);
        } else if (length == MAX_NAME_TAG - 1) {
            binding.setErrorText(false);
            binding.setEnableButtonSave(true);
        }
        if (length < 1) binding.setEnableButtonSave(false);
        else if (length < MAX_NAME_TAG - 1) binding.setEnableButtonSave(true);
    }
}
