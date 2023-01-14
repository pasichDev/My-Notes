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
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.databinding.DialogNewTagBinding;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.NewTagDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.NameTagDialogPresenter;

import java.util.Objects;

import javax.inject.Inject;

public class NameTagDialog extends BaseDialogBottomSheets implements NewTagDialogContract.view {


    private final Tag mTag;
    @Inject
    public NameTagDialogPresenter mPresenter;
    private DialogNewTagBinding binding;
    private boolean errorText = true;


    public NameTagDialog() {
        this.mTag = null;

    }

    public NameTagDialog(Tag tag) {

        this.mTag = tag;
    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogNewTagBinding.inflate(getLayoutInflater());
        requireDialog().setContentView(binding.getRoot());

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.attachView(this);
            mPresenter.viewIsReady();

            if (getTag() != null && getTag().equals("RenameTag") && mTag != null) {
                binding.nameTag.setText(mTag.getNameTag());
                binding.outlinedTextField.setEndIconDrawable(requireActivity().getDrawable(R.drawable.ic_rename));
            }

            binding.outlinedTextField.requestFocus();
        } else {
            dismiss();
        }


        return requireDialog();
    }


    @Override
    public void initListeners() {
        binding.outlinedTextField.setEndIconOnClickListener(v -> saveTag());


        binding.nameTag.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveTag();
                return true;
            } else return false;
        });
        binding.nameTag.addTextChangedListener(new TextWatcher() {

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
        if (length >= MAX_NAME_TAG + 1) {
            errorText = true;
            binding.outlinedTextField.setError(getString(R.string.errorNewTagInput, MAX_NAME_TAG));
        } else if (length == (MAX_NAME_TAG + 1) - 1) {
            errorText = false;
            binding.outlinedTextField.setError(null);
        }
        if (length < 1) errorText = true;
        else if (length < (MAX_NAME_TAG + 1) - 1) errorText = false;
    }


    private void saveTag() {
        if (!errorText) {
            if (getTag() != null && getTag().equals("RenameTag") && mTag != null) {

                mPresenter.editNameTag(Objects.requireNonNull(binding.nameTag.getText()).toString(), mTag);
            } else {

                mPresenter.saveTag(Objects.requireNonNull(binding.nameTag.getText()).toString());
            }
            dismiss();
        }
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mPresenter.detachView();
        binding.nameTag.addTextChangedListener(null);
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


}
