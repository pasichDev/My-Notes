package com.pasich.mynotes.ui.view.dialogs.trash;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.databinding.DialogCleanTrashBinding;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.ClearTrashDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.ClearTrashDialogPresenter;

import javax.inject.Inject;

public class CleanTrashDialog extends BaseDialogBottomSheets implements ClearTrashDialogContract.view {

    @Inject
    public ClearTrashDialogPresenter presenter;
    private DialogCleanTrashBinding binding;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogCleanTrashBinding.inflate(getLayoutInflater());
        requireDialog().setContentView(binding.getRoot());
        MaterialTextView title = binding.getRoot().findViewById(R.id.headTextDialog);
        MaterialTextView message = binding.getRoot().findViewById(R.id.textMessageDialog);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            presenter.attachView(this);
            presenter.viewIsReady();
        } else {
            dismiss();
        }

        title.setText(R.string.trashClean);
        message.setText(R.string.cleanTrashMessage);
        return requireDialog();
    }

    @Override
    public void initListeners() {
        binding.yesCleanTrash.setOnClickListener(v -> {
            presenter.clearTrash();
            dismiss();
        });
        binding.cancel.setOnClickListener(v -> dismiss());

    }



    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        presenter.detachView();
        binding.yesCleanTrash.setOnClickListener(null);
        binding.cancel.setOnClickListener(null);

    }
}
