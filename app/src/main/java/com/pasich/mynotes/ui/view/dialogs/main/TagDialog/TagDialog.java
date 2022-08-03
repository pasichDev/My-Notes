package com.pasich.mynotes.ui.view.dialogs.main.TagDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.ui.contract.dialog.TagDialogContract;
import com.pasich.mynotes.ui.presenter.dialog.TagDialogPresenter;
import com.pasich.mynotes.utils.adapters.TagsAdapter;

import java.util.List;


public class TagDialog extends DialogFragment implements TagDialogContract.view {

    private final Note note;
    private TagsAdapter tagsAdapter;
    private TagDialogView mView;
    private final TagDialogPresenter dialogPresenter;
    public final DataManager dataManager;
    BottomSheetDialog builder;

    public TagDialog(Note note) {
        this.note = note;
        this.dataManager = new DataManager();
        this.dialogPresenter = new TagDialogPresenter();

    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new BottomSheetDialog(requireContext());
        mView = new TagDialogView(getLayoutInflater());
        builder.setContentView(mView.getRootContainer());
        init();

        dialogPresenter.attachView(this);
        dialogPresenter.setDataManager(dataManager);
        dialogPresenter.viewIsReady();


        return builder;
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        dialogPresenter.detachView();
        mView = null;
        requireActivity()
                .getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public void init() {
        mView.setTitle(
                note.getTag().length() == 0
                        ? getString(R.string.selectTagForNote)
                        : getString(R.string.editSelectTagForNote));
        mView.goneInputNewTag();


        if (note.getTag().trim().length() >= 1) {
            mView.getRootContainer().findViewById(R.id.removeTagForDialog).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initListeners() {

        mView.getSaveButton()
                .setOnClickListener(
                        view1 -> {
                            dialogPresenter.createTagNote(new Tag().create(mView.getInputTag().getText().toString()), note);
                            dismiss();
                        });
        tagsAdapter.setOnItemClickListener(
                new TagsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        if (!tagsAdapter.getCurrentList().get(position).getNameTag().equals(note.getTag())) {
                            dialogPresenter.editTagNote(tagsAdapter.getCurrentList().get(position), note);
                            dismiss();
                        }
                    }

                    @Override
                    public void onLongClick(int position) {

                    }
                });

        mView.getRootContainer().findViewById(R.id.removeTagForDialog)
                .setOnClickListener(
                        view1 -> {
                            dialogPresenter.removeTagNote(note);
                            dismiss();
                        });
        mView.getRootContainer().findViewById(R.id.addTagForDialog)
                .setOnClickListener(
                        view1 -> {
                            mView.visibilityInputNewTag();
                            mView.getRootContainer().findViewById(R.id.addTagForDialog).setVisibility(View.GONE);
                            mView.getInputTag().requestFocus();
                            builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            ((InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);

                        });
    }

    @Override
    public void settingsActionBar() {

    }

    @Override
    public void settingsTagsList(int countColumn, LiveData<List<Tag>> tagsList) {
        tagsAdapter = new TagsAdapter(new TagsAdapter.tagDiff());
        mView.listTags.setAdapter(tagsAdapter);
        tagsList.observe(
                this,
                tags -> {
                    tagsAdapter.submitList(tags);
                    if (note.getTag().trim().length() >= 1) tagsAdapter.autChoseTag(note.getTag());
                });
    }


}
