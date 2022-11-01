package com.pasich.mynotes.ui.view.dialogs.trash;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.ClearTrashDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.ClearTrashDialogPresenter;

import javax.inject.Inject;

public class CleanTrashDialog extends BaseDialogBottomSheets implements ClearTrashDialogContract.view {

    @Inject
    public ClearTrashDialogPresenter presenter;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        requireDialog().setContentView(R.layout.dialog_clean_trash);
        MaterialTextView title = requireDialog().findViewById(R.id.headTextDialog);
        MaterialTextView message = requireDialog().findViewById(R.id.textMessageDialog);

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
        requireDialog().findViewById(R.id.yesCleanTrash).setOnClickListener(v -> {
            presenter.clearTrash();
            dismiss();
        });
        requireDialog().findViewById(R.id.cancel).setOnClickListener(v -> dismiss());

    }


}
