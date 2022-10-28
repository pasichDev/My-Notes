package com.pasich.mynotes.ui.view.dialogs.main;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_NAME_TAG;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.DialogNewTagBinding;

public class NewTagDialog extends BottomSheetDialogFragment {

    private DialogNewTagBinding binding;
    private boolean errorText = true;

    public NewTagDialog() {

    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext(), R.style.InputsDialog);
        binding = DialogNewTagBinding.inflate(getLayoutInflater());
        builder.setContentView(binding.getRoot());
        builder.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        binding.titleInclud.headTextDialog.setText(R.string.addTag);


        binding.includedInput.outlinedTextField.requestFocus();
        binding.titleInclud.closeDialog.setVisibility(View.VISIBLE);
        binding.titleInclud.closeDialog.setOnClickListener(v -> dismiss());

        binding.includedInput.outlinedTextField.setEndIconOnClickListener(v -> saveTag());
        binding.includedInput.nameTag.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveTag();
                return true;
            }
            return false;
        });

        binding.includedInput.nameTag.addTextChangedListener(
                new TextWatcher() {

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

        return builder;
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
            //      repository.addTag(new Tag().create(Objects.requireNonNull(binding.includedInput.nameTag.getText()).toString()));
            dismiss();
        }
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


}
