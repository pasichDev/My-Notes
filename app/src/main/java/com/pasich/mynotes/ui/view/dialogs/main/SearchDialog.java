package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.BaseDialogBottomSheets;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.databinding.DialogSearchBinding;
import com.pasich.mynotes.ui.contract.SearchDialogContract;
import com.pasich.mynotes.ui.presenter.SearchDialogPresenter;
import com.pasich.mynotes.ui.view.activity.NoteActivity;
import com.pasich.mynotes.utils.adapters.searchAdapter.SearchNotesAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class SearchDialog extends BaseDialogBottomSheets implements SearchDialogContract.view {

    private final SearchDialogPresenter searchDialogPresenter; // @Inject
    private DialogSearchBinding binding;
    private BottomSheetDialog builder;
    private SearchNotesAdapter searchNotesAdapter;  // @Inject
    private FloatingActionButton fabNewNote;

    public SearchDialog() {
        this.searchDialogPresenter = null;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new BottomSheetDialog(requireContext(), R.style.SearchDialog);
        binding = DialogSearchBinding.inflate(getLayoutInflater());
        builder.setContentView(binding.getRoot());

        searchDialogPresenter.attachView(this);
        searchDialogPresenter.viewIsReady();
        fabNewNote.hide();
        builder.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        binding.actionSearch.requestFocus();

        return builder;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        fabNewNote.show();
        onDestroy();

    }

    @Override
    public void initFabButton() {
        this.fabNewNote = requireActivity().findViewById(R.id.newNotesButton);
    }



    @Override
    public void initListeners() {
        binding.closeSearch.setOnClickListener(v -> {
            binding.actionSearch.clearFocus();
            dismiss();
        });
        searchNotesAdapter.setItemClickListener(idNote -> startActivity(new Intent(requireActivity(), NoteActivity.class).putExtra("NewNote", false).putExtra("idNote", idNote).putExtra("shareText", "").putExtra("tagNote", "")));


    }


    private void filter(String text, List<Note> allNotes) {
        ArrayList<Note> newFilter = new ArrayList<>();

        for (Note item : allNotes) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase()) && text.trim().length() >= 2) {
                newFilter.add(item);
            }
        }
        if (newFilter.isEmpty()) {
            searchNotesAdapter.cleanResult();
        } else {

            searchNotesAdapter.filterList(newFilter);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        builder = null;
        searchNotesAdapter = null;
        searchDialogPresenter.detachView();

    }

    @Override
    public void settingsListResult() {
        binding.resultsList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true));

        binding.resultsList.addItemDecoration(new SpacesItemDecoration(25));
        searchNotesAdapter = new SearchNotesAdapter();
        binding.resultsList.setAdapter(searchNotesAdapter);


    }

    @Override
    public void createListenerSearch(List<Note> mNotes) {
        binding.actionSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText, mNotes);
                return false;
            }
        });


    }

}
