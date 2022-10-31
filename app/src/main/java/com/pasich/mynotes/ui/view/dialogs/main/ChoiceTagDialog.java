package com.pasich.mynotes.ui.view.dialogs.main;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.ChoiceTagDialogContract;
import com.pasich.mynotes.ui.presenter.ChoiceTagDialogPresenter;

import javax.inject.Inject;

public class ChoiceTagDialog extends BaseDialogBottomSheets implements ChoiceTagDialogContract.view {

    private Tag mTag;
    @Inject
    public ChoiceTagDialogPresenter mPresenter;
    private SwitchCompat switchVisibility;


    public ChoiceTagDialog(Tag mTag) {
        this.mTag = mTag;
    }

    @SuppressLint("StringFormatMatches")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        vibrateOpenDialog(true);
        requireDialog().setContentView(R.layout.dialog_choice_tag);

        MaterialTextView title = requireDialog().findViewById(R.id.headTextDialog);
        switchVisibility = requireDialog().findViewById(R.id.switchVisibilityTag);
        MaterialTextView infoItem = requireDialog().findViewById(R.id.noteInfo);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.attachView(this);
            mPresenter.viewIsReady();
        } else {
            dismiss();
        }


        title.setText(mTag.getNameTag());
        switchVisibility.setChecked(mTag.getVisibility() == 1);
        infoItem.setText(getString(R.string.layoutStringInfoTags, mPresenter.getLoadCountNotesForTag(mTag.getNameTag())));


        return requireDialog();
    }

    @Override
    public void initListeners() {
        requireDialog().findViewById(R.id.deleteTagLayout).setOnClickListener(v -> {
            mPresenter.deleteTagInitial(mTag);

        });


        switchVisibility.setOnCheckedChangeListener((buttonView, isChecked) -> mPresenter.editVisibilityTag(mTag.setVisibilityReturn(isChecked ? 1 : 0)));
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        requireDialog().findViewById(R.id.deleteTagLayout).setOnClickListener(null);
        switchVisibility.setOnCheckedChangeListener(null);
        mTag = null;
    }

    @Override
    public void startDeleteTagDialog() {
        new DeleteTagDialog(mTag).show(getChildFragmentManager(), "deleteTag");
    }

}
