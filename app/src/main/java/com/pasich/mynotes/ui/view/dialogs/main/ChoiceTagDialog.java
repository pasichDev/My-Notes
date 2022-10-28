package com.pasich.mynotes.ui.view.dialogs.main;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.TagView;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.utils.override.DialogOpenVibrate;

public class ChoiceTagDialog extends DialogOpenVibrate {

    private final Integer[] keysNoteInfo;
    private final Tag tag;

    public ChoiceTagDialog(Tag tag, Integer[] keysNoteInfo) {
        this.keysNoteInfo = keysNoteInfo;
        this.tag = tag;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
        final TagView tagView = (TagView) getContext();
        final int countNotes = keysNoteInfo[0];
        builder.setContentView(R.layout.dialog_choice_tag);

        builder.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        MaterialTextView title = builder.findViewById(R.id.headTextDialog);
        assert title != null;
        title.setText(tag.getNameTag());

        MaterialTextView infoItem = builder.findViewById(R.id.noteInfo);
        assert infoItem != null;
        infoItem.setText(
                getString(R.string.layoutStringInfoTags, String.valueOf(countNotes)));


        builder.findViewById(R.id.deleteTagLayout).setOnClickListener(v -> {
            if (countNotes == 0) {
                assert tagView != null;
                tagView.deleteTag(tag, false);
            } else {
                new DeleteTagDialog(countNotes, tag)
                        .show(getParentFragmentManager(), "deleteTag");
            }
            dismiss();
        });

        SwitchCompat switchVisibility = builder.findViewById(R.id.switchVisibilityTag);
        assert switchVisibility != null;
        switchVisibility.setChecked(tag.getVisibility() == 1);
        switchVisibility.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    assert tagView != null;
                    tag.setVisibility(isChecked ? 1 : 0);
                    tagView.editVisibility(tag);
                });
        return builder;
    }
}
