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
import com.pasich.mynotes.ui.contract.dialogs.ChoiceTagDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.ChoiceTagDialogPresenter;

import javax.inject.Inject;

public class ChoiceTagDialog extends BaseDialogBottomSheets implements ChoiceTagDialogContract.view {

    private final Tag mTag;
    @Inject
    public ChoiceTagDialogPresenter mPresenter;
    private SwitchCompat switchVisibility;
    private MaterialTextView infoItem;

    public ChoiceTagDialog(Tag mTag) {
        this.mTag = mTag;
    }

    @SuppressLint("StringFormatMatches")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        vibrateOpenDialog(true);
        requireDialog().setContentView(R.layout.dialog_choice_tag);

        MaterialTextView title = requireDialog().findViewById(R.id.headTextDialog);
        infoItem = requireDialog().findViewById(R.id.noteInfo);
        switchVisibility = requireDialog().findViewById(R.id.switchVisibilityTag);


        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.attachView(this);
            mPresenter.viewIsReady();
            mPresenter.getLoadCountNotesForTag(mTag.getNameTag());
        } else {
            dismiss();
        }


        title.setText(mTag.getNameTag());
        switchVisibility.setChecked(mTag.getVisibility() == 1);


        return requireDialog();
    }


    @Override
    public void onStart() {
        super.onStart();
        infoItem.setText(getString(R.string.layoutStringInfoTags, String.valueOf(mPresenter.getCountNotesForTag())));
    }

    @Override
    public void initListeners() {
        requireDialog().findViewById(R.id.deleteTagLayout).setOnClickListener(v -> {
            mPresenter.deleteTagInitial(mTag);
            dismiss();
        });


        switchVisibility.setOnCheckedChangeListener((buttonView, isChecked) -> mPresenter.editVisibilityTag(mTag.setVisibilityReturn(isChecked ? 1 : 0)));
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        requireDialog().findViewById(R.id.deleteTagLayout).setOnClickListener(null);
        switchVisibility.setOnCheckedChangeListener(null);
        mPresenter.destroy();
    }


    @Override
    public void startDeleteTagDialog() {
        new DeleteTagDialog(mTag).show(getChildFragmentManager(), "deleteTag");
    }

}
