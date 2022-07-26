package com.pasich.mynotes.ui.view.dialogs.main.TagDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.NoteView;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.utils.adapters.TagsAdapter;

import java.util.List;


public class TagDialog extends DialogFragment {

    private final Note note;
    private final LiveData<List<Tag>> tagList;
    private TagsAdapter tagsAdapter;
    private TagDialogView mView;
    private NoteView noteView;

    public TagDialog(Note note, LiveData<List<Tag>> tagList) {

        this.note = note;
        this.tagList = tagList;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        mView = new TagDialogView(getLayoutInflater());
        noteView = (NoteView) getContext();
        final String noteTag = note.getTag();

        mView.setTitle(
                note.getTag().length() == 0
                        ? getString(R.string.selectTagForNote)
                        : getString(R.string.editSelectTagForNote));

        tagsAdapter = new TagsAdapter(new TagsAdapter.tagDiff());
        mView.listTags.setAdapter(tagsAdapter);
        tagList.observe(
                this,
                tags -> {
                    tagsAdapter.submitList(tags);
                    if (noteTag.trim().length() >= 1) tagsAdapter.autChoseTag(noteTag);
                });

        if (noteTag.trim().length() >= 1) {
            mView.getRootContainer().findViewById(R.id.removeTagForDialog).setVisibility(View.VISIBLE);
        }


        mView.getSaveButton()
                .setOnClickListener(
                        view1 -> {
                            assert noteView != null;
                            noteView.editTagForNote(new Tag().create(mView.getInputTag().getText().toString()), note);
                            dismiss();
                        });
        tagsAdapter.setOnItemClickListener(
                new TagsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        noteView.editTagForNote(tagsAdapter.getCurrentList().get(position), note);
                        dismiss();
                    }

                    @Override
                    public void onLongClick(int position) {

                    }
                });

        builder.setContentView(mView.getRootContainer());
        builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return builder;
    }



    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        requireActivity()
                .getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
