package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.TagView;
import com.pasich.mynotes.data.tags.Tag;


public class DeleteTagDialog extends DialogFragment {

    private final int countNotesToTag;
    private final Tag tag;

    public DeleteTagDialog(int countNotesToTag, Tag tag) {
        this.countNotesToTag = countNotesToTag;
        this.tag = tag;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        final TagView tagView = (TagView) getContext();

        builder.setContentView(R.layout.dialog_delete_tag);

        MaterialTextView title = builder.findViewById(R.id.headTextDialog);
        title.setText(R.string.deleteTag);

        MaterialTextView message = builder.findViewById(R.id.textMessageDialog);
        message.setText(R.string.deleteSelectTagTextMassage);


        builder.findViewById(R.id.deleteTag).setOnClickListener(v -> {
            assert tagView != null;
            tagView.deleteTag(tag, false);
            dismiss();
        });

        if (countNotesToTag != 0) {
            LinearLayout deleteTagAndNotes = builder.findViewById(R.id.deleteTagAndNotes);
            assert deleteTagAndNotes != null;
            deleteTagAndNotes.setVisibility(View.VISIBLE);
            deleteTagAndNotes.setOnClickListener(v -> {
                assert tagView != null;
                tagView.deleteTag(tag, true);
                dismiss();
            });

        }
        builder.findViewById(R.id.cancel).setOnClickListener(v -> dismiss());

        return builder;
    }
}
