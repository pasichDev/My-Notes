package com.pasich.mynotes.ui.view.dialogs.main.TagDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.NoteView;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.utils.adapters.TagsAdapter;
import com.pasich.mynotes.utils.adapters.TagsDialogAdapter;

import java.util.ArrayList;
import java.util.List;


public class TagDialog extends DialogFragment {

    private final Note note;
    private final LiveData<List<Tag>> tagList;


    public TagDialog(Note note, LiveData<List<Tag>> tagList) {

        this.note = note;
        this.tagList = tagList;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        final TagDialogView mView = new TagDialogView(getLayoutInflater());
        final NoteView noteView = (NoteView) getContext();


      TagsAdapter  tagsAdapter = new TagsAdapter(new TagsAdapter.tagDiff());
        mView.listTags.setAdapter(tagsAdapter);
        tagList.observe(
                this,
                tags -> {
                    tagsAdapter.submitList(tags);
                });
        mView.setTitle(
                note.getTag().length() == 0
                        ? getString(R.string.selectTagForNote)
                        : getString(R.string.editSelectTagForNote));

        mView
                .getSaveButton()
                .setOnClickListener(
                        view1 -> {
                            noteView.editTagForNote(new Tag().create(mView.getInputTag().getText().toString()), note);
                            //           tagView.addTag(mView.getInputTag().getText().toString());
                            dismiss();
                        });

        builder.setContentView(mView.getRootContainer());
        builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return builder;
    }



}
