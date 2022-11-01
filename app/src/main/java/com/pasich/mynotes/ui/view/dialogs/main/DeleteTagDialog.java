package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
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

    private final Tag tag;
    @Inject
    public DeleteTagDialogPresenter mPresenter;

    public DeleteTagDialog(Tag tag) {
        this.tag = tag;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        requireDialog().setContentView(R.layout.dialog_delete_tag);
        MaterialTextView title = requireDialog().findViewById(R.id.headTextDialog);
        MaterialTextView message = requireDialog().findViewById(R.id.textMessageDialog);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.attachView(this);
            mPresenter.viewIsReady();
        } else {
            dismiss();
        }

        title.setText(R.string.deleteTag);
        message.setText(R.string.deleteSelectTagTextMassage);
        return requireDialog();
    }

    @Override
    public void initListeners() {
        requireDialog().findViewById(R.id.deleteTag).setOnClickListener(v -> {
            //   assert tagView != null;
            //   tagView.deleteTag(tag, false);
            dismiss();
        });

        if (mPresenter.getLoadCountNotesForTag(tag.getNameTag()) != 0) {
            LinearLayout deleteTagAndNotes = requireDialog().findViewById(R.id.deleteTagAndNotes);
            assert deleteTagAndNotes != null;
            deleteTagAndNotes.setVisibility(View.VISIBLE);
            deleteTagAndNotes.setOnClickListener(v -> {
                //       assert tagView != null;
                //       tagView.deleteTag(tag, true);
                dismiss();
            });

        }
        requireDialog().findViewById(R.id.cancel).setOnClickListener(v -> dismiss());

    }
}
