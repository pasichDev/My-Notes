package com.pasich.mynotes.ui.view.dialogs.note;

import android.app.Dialog;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.NoteActivityView;
import com.pasich.mynotes.data.model.ChoiceModel;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.utils.GoogleTranslationIntent;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;

import java.util.ArrayList;

public class MoreNoteDialog extends DialogFragment {

    private final Note mNote;
    private final boolean typeActivity;

    public MoreNoteDialog(Note note, boolean typeNote) {
        this.mNote = note;
        this.typeActivity = typeNote;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        final ListView listView = new ListView(requireContext());
        final ArrayList<ChoiceModel> arraySortOption = new ArrayList<>();
        final NoteActivityView noteActivityView = (NoteActivityView) getContext();

        listView.setLayoutAnimation(
                new LayoutAnimationController(
                        AnimationUtils.loadAnimation(listView.getContext(), R.anim.item_animation_dialog)));
        listView.setDivider(null);

        arraySortOption.add(
                new ChoiceModel(getString(R.string.share), R.drawable.ic_share, "Share", false));
        arraySortOption.add(
                new ChoiceModel(
                        getString(R.string.translateNote), R.drawable.ic_translate, "GoogleTanslationIntent", false));

        if (!typeActivity) {
            arraySortOption.add(
                    new ChoiceModel(getString(R.string.trashNotes), R.drawable.ic_delete, "Delete", false));
        }
        arraySortOption.add(
                new ChoiceModel(
                        getString(R.string.noSave), R.drawable.ic_close_search_view, "Close", false));
        DialogListAdapter adapter = new DialogListAdapter(arraySortOption);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
                (parent, v, position, id) -> {
                    if (adapter.getItem(position).getAction().equals("Close")) {
                        assert noteActivityView != null;
                        noteActivityView.closeActivityNotSaved();
                    }
                    if (adapter.getItem(position).getAction().equals("GoogleTanslationIntent")) {
                        new GoogleTranslationIntent().startTranslation(getActivity(), mNote.getValue());
                    }
                    if (adapter.getItem(position).getAction().equals("Share")) {
                        new ShareUtils(mNote, getActivity()).shareNotes();
                    }
                    if (adapter.getItem(position).getAction().equals("Delete")) {
                        assert noteActivityView != null;
                        noteActivityView.deleteNote(mNote);
                    }
                    dismiss();
                });
        builder.setContentView(listView);
        return builder;
    }
}
