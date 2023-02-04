package com.pasich.mynotes.ui.view.dialogs.trash;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.databinding.DialogCleanTrashBinding;
import com.pasich.mynotes.ui.contract.dialogs.ClearTrashDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.ClearTrashDialogPresenter;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CleanTrashDialog extends BaseDialogBottomSheets implements ClearTrashDialogContract.view {

    @Inject
    public ClearTrashDialogPresenter presenter;
    private DialogCleanTrashBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogCleanTrashBinding.inflate(getLayoutInflater());
        presenter.attachView(this);
        presenter.viewIsReady();
        binding.title.headTextDialog.setText(R.string.trashClean);
        binding.textMessageDialog.setText(R.string.cleanTrashMessage);
        return binding.getRoot();
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
