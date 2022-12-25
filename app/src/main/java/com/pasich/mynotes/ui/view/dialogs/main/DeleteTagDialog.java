package com.pasich.mynotes.ui.view.dialogs.main;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.databinding.DialogDeleteTagBinding;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.DeleteTagDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.DeleteTagDialogPresenter;

import javax.inject.Inject;


public class DeleteTagDialog extends BaseDialogBottomSheets implements DeleteTagDialogContract.view {

    @Inject
    public DeleteTagDialogPresenter mPresenter;
    private Tag tag;
    private DialogDeleteTagBinding binding;


    public DeleteTagDialog(Tag tag) {
        this.tag = tag;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogDeleteTagBinding.inflate(getLayoutInflater());
        requireDialog().setContentView(binding.getRoot());
        MaterialTextView title = binding.getRoot().findViewById(R.id.headTextDialog);
        MaterialTextView message = binding.getRoot().findViewById(R.id.textMessageDialog);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.attachView(this);
            mPresenter.viewIsReady();
            mPresenter.getLoadCountNotesForTag(tag.getNameTag());
        } else {
            dismiss();
        }

        title.setText(R.string.deleteTag);
        message.setText(R.string.deleteSelectTagTextMassage);
        return requireDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.deleteTagAndNotes.setVisibility(mPresenter.getCountNotesForTag() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void initListeners() {
        binding.deleteTag.setOnClickListener(v -> {
            mPresenter.deleteTagUnchecked(tag);
            dismiss();
        });


        binding.deleteTagAndNotes.setOnClickListener(v -> {
            mPresenter.deleteTagAndNotes(tag);
            dismiss();
        });


        binding.cancel.setOnClickListener(v -> dismiss());

    }

    @Override
    public void redrawActivity() {

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mPresenter.detachView();
        binding.deleteTag.setOnClickListener(null);
        binding.deleteTagAndNotes.setOnClickListener(null);
        binding.cancel.setOnClickListener(null);
        tag = null;
    }
}
