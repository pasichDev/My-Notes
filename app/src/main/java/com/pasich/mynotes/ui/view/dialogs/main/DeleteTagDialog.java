package com.pasich.mynotes.ui.view.dialogs.main;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.DeleteTagDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.DeleteTagDialogPresenter;

import javax.inject.Inject;


public class DeleteTagDialog extends BaseDialogBottomSheets implements DeleteTagDialogContract.view {

    private Tag tag;
    @Inject
    public DeleteTagDialogPresenter mPresenter;
    private LinearLayout deleteTagAndNotes;


    public DeleteTagDialog(Tag tag) {
        this.tag = tag;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        requireDialog().setContentView(R.layout.dialog_delete_tag);
        MaterialTextView title = requireDialog().findViewById(R.id.headTextDialog);
        MaterialTextView message = requireDialog().findViewById(R.id.textMessageDialog);
        deleteTagAndNotes = requireDialog().findViewById(R.id.deleteTagAndNotes);
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
        deleteTagAndNotes.setVisibility(mPresenter.getCountNotesForTag() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void initListeners() {
        requireDialog().findViewById(R.id.deleteTag).setOnClickListener(v -> {
            mPresenter.deleteTagUnchecked(tag);
            dismiss();
        });


        deleteTagAndNotes.setOnClickListener(v -> {
            mPresenter.deleteTagAndNotes(tag);
            dismiss();
        });


        requireDialog().findViewById(R.id.cancel).setOnClickListener(v -> dismiss());

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mPresenter.destroy();
        requireDialog().findViewById(R.id.deleteTag).setOnClickListener(null);
        deleteTagAndNotes.setOnClickListener(null);
        requireDialog().findViewById(R.id.cancel).setOnClickListener(null);
        tag = null;
        deleteTagAndNotes = null;
    }
}
