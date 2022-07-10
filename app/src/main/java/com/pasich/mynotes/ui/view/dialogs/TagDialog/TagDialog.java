package com.pasich.mynotes.ui.view.dialogs.TagDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.NoteView;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.utils.adapters.TagsDialogAdapter;


public class TagDialog extends DialogFragment {

    private final Note note;


    public TagDialog(Note note) {
        this.note = note;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        final TagDialogView mView = new TagDialogView(getLayoutInflater());
        final DataManager dataManager = new DataManager();
        final NoteView noteView = (NoteView) getContext();



        /*
        Тут весь трабла в тому що данні підгружаються пізніше ніж потрібно!
         */

        TagsDialogAdapter tagsAdapter = new TagsDialogAdapter(dataManager.getTagsRepository().getTagsList());
        mView.listTags.setAdapter(tagsAdapter);


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
