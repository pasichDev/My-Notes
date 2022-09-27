package com.pasich.mynotes.ui.view.dialogs.note;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.DialogSourceNoteBinding;
import com.pasich.mynotes.utils.SearchSourceNote;
import com.pasich.mynotes.utils.adapters.SourceAdapter;

public class SourceNoteDialog extends BottomSheetDialogFragment {
    private final SearchSourceNote searchSourceNote;


    public SourceNoteDialog(SearchSourceNote searchSourceNote) {
        this.searchSourceNote = searchSourceNote;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        final DialogSourceNoteBinding binding = DialogSourceNoteBinding.inflate(getLayoutInflater());
        final SourceAdapter mSourceAdapter = new SourceAdapter(searchSourceNote.getListArray());
        builder.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        builder.getBehavior().setHideable(false);
        builder.setContentView(binding.getRoot());
        binding.titleInclude.headTextDialog.setText(getString(R.string.sourceNotes));

        MaterialTextView infoItem = builder.findViewById(R.id.noteInfo);
        assert infoItem != null;
        infoItem.setText(getString(R.string.infoDialogSource));

        binding.titleInclude.closeDialog.setVisibility(View.VISIBLE);
        binding.titleInclude.closeDialog.setOnClickListener(v -> dismiss());

        binding.mListSource.setAdapter(mSourceAdapter);
        binding.mListSource.setOnItemClickListener(
                (parent, v, position, id) -> {
                    Intent intent = null;
                    if (mSourceAdapter.getItem(position).getType().equals("Mail")) {
                        intent = new Intent(Intent.ACTION_SENDTO)
                                .setData(Uri.parse("mailto:" + mSourceAdapter.getItem(position).getSource()));
                    }
                    if (mSourceAdapter.getItem(position).getType().equals("Url")) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mSourceAdapter.getItem(position).getSource()));
                    }
                    if (mSourceAdapter.getItem(position).getType().equals("Tel")) {
                        intent = new Intent(Intent.ACTION_DIAL)
                                .setData(Uri.parse("tel:" + mSourceAdapter.getItem(position).getSource()));
                    }
                    assert intent != null;
                    if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                });

        binding.mListSource.setOnItemLongClickListener((parent, v, position, id) -> {
            String selectedItem = mSourceAdapter.getItem(position).getSource();
            ClipboardManager clipboard =
                    (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText(selectedItem, selectedItem));
            Toast.makeText(requireContext(), getString(R.string.copyX) + " " + selectedItem, Toast.LENGTH_SHORT)
                    .show();
            return true;
        });

        return builder;
    }

}
