package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.databinding.DialogChoiceTagBinding;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.ChoiceTagDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.ChoiceTagDialogPresenter;

import javax.inject.Inject;

public class ChoiceTagDialog extends BaseDialogBottomSheets implements ChoiceTagDialogContract.view {

    private final Tag mTag;
    @Inject
    public ChoiceTagDialogPresenter mPresenter;
    private DialogChoiceTagBinding binding;

    public ChoiceTagDialog(Tag mTag) {
        this.mTag = mTag;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        vibrateOpenDialog(true);
        binding = DialogChoiceTagBinding.inflate(getLayoutInflater());
        requireDialog().setContentView(binding.getRoot());

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.attachView(this);
            mPresenter.viewIsReady();
            mPresenter.getLoadCountNotesForTag(mTag.getNameTag());
        } else {
            dismiss();
        }


        binding.includeHead.headTextDialog.setText(mTag.getNameTag());
        binding.switchVisibilityTag.setChecked(mTag.getVisibility() == 1);


        return requireDialog();
    }


    @Override
    public void onStart() {
        super.onStart();
        binding.includedInfo.noteInfo.setText(getString(R.string.layoutStringInfoTags, String.valueOf(mPresenter.getCountNotesForTag())));
    }

    @Override
    public void initListeners() {
        requireDialog().findViewById(R.id.deleteTagLayout).setOnClickListener(v -> {
            mPresenter.deleteTagInitial(mTag);
            dismiss();
        });


        binding.switchVisibilityTag.setOnCheckedChangeListener((buttonView, isChecked) -> mPresenter.editVisibilityTag(mTag.setVisibilityReturn(isChecked ? 1 : 0)));
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        requireDialog().findViewById(R.id.deleteTagLayout).setOnClickListener(null);
        binding.switchVisibilityTag.setOnCheckedChangeListener(null);
        mPresenter.destroy();
    }


    @Override
    public void startDeleteTagDialog() {
        new DeleteTagDialog(mTag).show(getParentFragmentManager(), "deleteTag");
    }

}
