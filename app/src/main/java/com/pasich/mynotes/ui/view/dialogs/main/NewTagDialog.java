package com.pasich.mynotes.ui.view.dialogs.main;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_NAME_TAG;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.databinding.DialogNewTagBinding;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.NewTagDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.NewTagDialogPresenter;

import java.util.Objects;

import javax.inject.Inject;

public class NewTagDialog extends BaseDialogBottomSheets implements NewTagDialogContract.view {


    @Inject
    public NewTagDialogPresenter mPresenter;
    private DialogNewTagBinding binding;
    private boolean errorText = true;


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogNewTagBinding.inflate(getLayoutInflater());
        requireDialog().setContentView(binding.getRoot());

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.attachView(this);
            mPresenter.viewIsReady();
        } else {
            dismiss();
        }

        binding.includedInput.outlinedTextField.requestFocus();
        binding.includedInput.outlinedTextField.setEndIconOnClickListener(v -> saveTag());


        binding.includedInput.nameTag.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveTag();
                return true;
            } else return false;
        });

        return requireDialog();
    }


    @Override
    public void initListeners() {
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
            mPresenter.saveTag(Objects.requireNonNull(binding.includedInput.nameTag.getText()).toString());
            dismiss();
        }
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mPresenter.detachView();
        binding.includedInput.nameTag.addTextChangedListener(null);
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


}
