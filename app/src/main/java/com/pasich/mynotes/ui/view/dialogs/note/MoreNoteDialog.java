package com.pasich.mynotes.ui.view.dialogs.note;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.NoteActivityView;
import com.pasich.mynotes.data.model.ChoiceModel;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;
import com.pasich.mynotes.utils.GoogleTranslationIntent;
import com.pasich.mynotes.utils.ShareUtils;
import com.pasich.mynotes.utils.ShortCutUtils;
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
        final ListDialogView view = new ListDialogView(getLayoutInflater());

        final NoteActivityView noteActivityView = (NoteActivityView) getContext();


        DialogListAdapter adapter = new DialogListAdapter(initList());
        view.getItemsView().setAdapter(adapter);
        view.getItemsView().setOnItemClickListener(
                (parent, v, position, id) -> {
                    if (adapter.getItem(position).getAction().equals("Close")) {
                        assert noteActivityView != null;
                        noteActivityView.closeActivityNotSaved();
                    }
                    if (adapter.getItem(position).getAction().equals("GoogleTranslationIntent")) {
                        new GoogleTranslationIntent().startTranslation(requireActivity(), mNote.getValue());
                    }
                    if (adapter.getItem(position).getAction().equals("Share")) {
                        new ShareUtils(mNote, getActivity()).shareNotes();
                    }
                    if (adapter.getItem(position).getAction().equals("Delete")) {
                        assert noteActivityView != null;
                        noteActivityView.deleteNote(mNote);
                    }
                    if (adapter.getItem(position).getAction().equals("addShotCut") &&
                            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        ShortCutUtils.createShortCut(mNote, getContext());
                    }
                    dismiss();
                });
        view.addView(view.getItemsView());
        builder.setContentView(view.getRootContainer());
        return builder;
    }

    private ArrayList<ChoiceModel> initList() {
        final ArrayList<ChoiceModel> arraySortOption = new ArrayList<>();
        arraySortOption.add(
                new ChoiceModel(getString(R.string.share), R.drawable.ic_share, "Share", false));

        if (mNote.getValue().length() >= 5) arraySortOption.add(
                new ChoiceModel(
                        getString(R.string.translateNote), R.drawable.ic_translate, "GoogleTranslationIntent", false));

        if (!typeActivity) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                arraySortOption.add(new ChoiceModel(getString(R.string.addShortCutLauncher), R.drawable.ic_label, "addShotCut", false));
            }
            arraySortOption.add(
                    new ChoiceModel(getString(R.string.trashNotes), R.drawable.ic_delete_note, "Delete", false));
        }
        arraySortOption.add(
                new ChoiceModel(
                        getString(R.string.noSave), R.drawable.ic_close_search_view, "Close", false));

        return arraySortOption;
    }
}
